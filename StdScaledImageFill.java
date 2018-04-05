package com.lisebellamy.util;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;

class StdScaledImageFill
    implements ScaledImage
{
    private final BufferedImage imageIn;
    private BufferedImage imageOut = null;
    private final int width;
    private final int height;
    private Color fillColor;
    private ImageCoords coords = null;

    StdScaledImageFill (BufferedImage imageInArg,
			int widthArg, int heightArg,
			Color fillColorArg,
			ImageCoords coordsArg)
    {
	this (imageInArg, widthArg, heightArg,
	      fillColorArg);
	this.coords = coordsArg;
    }
    
    StdScaledImageFill (BufferedImage imageInArg,
			int widthArg, int heightArg,
			Color fillColorArg)
    {
	this.width = widthArg;
	this.height = heightArg;
	this.fillColor = fillColorArg;
	this.imageIn = new BufferedImage (widthArg, heightArg,
					  BufferedImage.TYPE_INT_ARGB);
	int toDrawX;
	int toDrawY;
	int toDrawW;
	int toDrawH;
	if (imageInArg.getWidth () <= width && imageInArg.getHeight () <= height)
	    {
		toDrawW = imageInArg.getWidth ();
		toDrawH = imageInArg.getHeight ();
		toDrawX = (width / 2) - (imageInArg.getWidth () / 2);
		toDrawY = (height / 2) - (imageInArg.getHeight () / 2);
	    }
	else
	    {
		if ((width / (double) imageInArg.getWidth ()) > (height / (double) imageIn.getHeight ()))
		    {
			double ratio = width / imageInArg.getWidth ();
			toDrawW = width;
			toDrawH = (int) (imageInArg.getHeight () * ratio);
			toDrawX = 0;
			toDrawY = height / 2 - toDrawH / 2;
		    }
		else
		    {
			double ratio = height / imageInArg.getHeight ();
			toDrawW = (int) (imageInArg.getWidth () * ratio);
			toDrawH = height;
			toDrawX = width / 2 - toDrawW / 2;
			toDrawY = 0;
		    }
		assert toDrawW <= width;
		assert toDrawH <= height;
	    }
	Graphics2D g2d = this.imageIn.createGraphics ();
	g2d.setRenderingHint (RenderingHints.KEY_INTERPOLATION,
			      RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	g2d.setComposite (AlphaComposite.getInstance (AlphaComposite.SRC));
	g2d.drawImage (imageInArg,
		       toDrawX, toDrawY, toDrawW, toDrawH,
		       null);
	g2d.dispose ();
    }

    public BufferedImage getImage (int width, int height)
    {
	if (this.imageOut == null
	    || this.imageOut.getWidth () != width
	    || this.imageOut.getHeight () != height)
	    {
		this.createScaledImage (width, height);
	    }
	return this.imageOut;
    }

    private void createScaledImage (int width, int height)
    {
	BufferedImage res = new BufferedImage (width,
					       height,
					       BufferedImage.TYPE_INT_ARGB);
	
	Graphics2D gt = res.createGraphics();
	gt.setRenderingHint (RenderingHints.KEY_INTERPOLATION,
			     RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	gt.drawImage (this.imageIn,
		      0, 0, width, height,
		      0, 0,
		      this.imageIn.getWidth (), this.imageIn.getHeight (),
		      null);
	gt.dispose();
	this.imageOut = res;
    }

    public int getRawWidth ()
    {
	return this.imageIn.getWidth ();
    }

    public int getRawHeight ()
    {
	return this.imageIn.getHeight ();
    }

    public BufferedImage getRawImage ()
    {
	return this.imageIn;
    }

    public double getAspectRatio ()
    {
	return ((double) this.imageIn.getWidth ()) / this.imageIn.getHeight ();
    }
}
