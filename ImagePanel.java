package com.lisebellamy.util;

import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import javax.accessibility.*;
import java.util.Map;
import java.util.HashMap;

import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.sound.midi.MidiMessage;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.text.*;
import java.beans.*;

public class ImagePanel extends JPanel
{
    private ImageList images = new ImageList ();
    private boolean displayingImage = true;
    private float alpha = 1.0f;

    public ImagePanel(ScaledImage imageArg)
    {
	if (imageArg != null)
	    {
		this.images.setImage (0, imageArg,
				      new ImageList.Position (0, 0, 1, 1));
	    }
    }

    public ImagePanel ()
    {
    }

    public boolean getImageDisplayState ()
    {
	return this.displayingImage;
    }

    public void setAlpha (float alphaArg)
    {
	this.alpha = alphaArg;
	this.validate ();
	this.repaint ();
    }

    public void setImageDisplayState (boolean flag)
    {
	this.displayingImage = flag;
    }

    public void setImageList (ImageList newList)
    {
	this.images = newList;
	this.validate ();
	this.repaint ();
    }

    public ImageList getImageList () { return this.images; }
    public int getNumImages () { return this.images.getNumberOfImages (); }

    public void setImage (ScaledImage imageArg)
    {
	this.images.clearImages ();
	this.images.setImage (0, imageArg, new ImageList.Position (0, 0, 1, 1));
	this.validate ();
	this.repaint ();
    }

    public void setImage (int whichImage, ScaledImage imageArg,
			  ImageList.Position positionArg)
    {
	this.images.setImage (whichImage, imageArg, positionArg);
	this.validate ();
	this.repaint ();
    }

    public void replaceImage (int whichImage, ScaledImage image)
    {
	if (this.images.replaceImage (whichImage, image))
	    {
		this.validate ();
		this.repaint ();
	    }
    }

    public ImageList.Position getBBoxForImage (int whichImage)
    {
	return this.images.getBBoxForImage (whichImage);
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
	super.paintComponent (g);
	Graphics2D g2d = (Graphics2D) g;
	if (this.displayingImage)
	    {
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		boolean setComposite = false;
		Composite oldComposite = null;
		if (this.alpha != 1.0)
		    {
			oldComposite = g2d.getComposite ();
			g2d.setComposite (AlphaComposite.getInstance (AlphaComposite.SRC_OVER, this.alpha));
			setComposite = true;
		    }
		int len = this.images.getNumberOfImages ();
		Rectangle r = new Rectangle (this.getWidth (),
					     this.getHeight (),
					     this.getWidth (),
					     this.getHeight ());
		for (int i = 0; i < len; i++)
		    {
			Rectangle rOut = this.images.scaleRectangleForImage (i, r);
			ScaledImage sImg = this.images.getImage (i);
			BufferedImage img = sImg.getImage ((int) (rOut.getWidth () + 0.5), (int) (rOut.getHeight () + 0.5));
			g.drawImage(img, (int) (rOut.getX () + 0.5), (int) (rOut.getY() + 0.5),
				    
				    this);
		    }
		if (setComposite)
		    {
			g2d.setComposite (oldComposite);
		    }
	    }
    }
}

