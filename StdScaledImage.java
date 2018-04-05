package com.lisebellamy.util;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;

public class StdScaledImage
    implements ScaledImage
{
    private final BufferedImage imageIn;
    private BufferedImage imageOut = null;
    private final ImageCoords coords;
    
    public StdScaledImage (BufferedImage imageInArg)
    {
	this.imageIn = imageInArg;
	this.coords = null;
    }

    public StdScaledImage (BufferedImage imageInArg, ImageCoords coordsArg)
    {
	this.imageIn = imageInArg;
	this.coords = coordsArg;
    }

    public BufferedImage getImage (int width, int height)
    {
	if (this.imageIn.getWidth () == width
	    && this.imageIn.getHeight () == height)
	    {
		return this.imageIn;
	    }
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

    public FracRectangle getImageCoord (String name)
    {
	return this.coords.getCoordForName (name);
    }
}
