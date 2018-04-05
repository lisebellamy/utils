package com.lisebellamy.util;

import java.awt.image.BufferedImage;
import com.lisebellamy.util.ScaledImage;
import javax.swing.*;
import javax.swing.border.*;
import javax.accessibility.*;
import java.util.Map;
import java.util.HashMap;

import javax.swing.event.*;

import java.awt.Rectangle;
import java.awt.event.*;
import java.util.*;
import javax.swing.text.*;
import java.beans.*;

public class ImageList
{
    static public class Position
    {
	private double x;
	private double y;
	private double w;
	private double h;
	private boolean centered;

	public Position (double xArg, double yArg,
			 double wArg, double hArg)
	{
	    this.x = xArg;
	    this.y = yArg;
	    this.w = wArg;
	    this.h = hArg;
	}

	public void setCentered (boolean centeredArg)
	{
	    this.centered = centeredArg;
	}

	public double getX ()
	{
	    return this.x;
	}
	
	public double getY ()
	{
	    return this.y;
	}
	
	public double getWidth ()
	{
	    return this.w;
	}
	
	public double getHeight ()
	{
	    return this.h;
	}

	public Rectangle scaleRectangle (Rectangle r)
	{
	    double xPos = r.getWidth () * this.x;
	    double yPos = r.getHeight () * this.y;

	    if (this.centered)
		{
		    xPos = xPos - r.getWidth () * this.w / 2;
		    yPos = yPos - r.getHeight () * this.h / 2;
		}

	    Rectangle res = new Rectangle ((int) xPos, (int) yPos,
					   (int) (r.getWidth () * this.w),
					   (int) (r.getHeight () * this.h));
	    return res;
	}
    }

    private List<Position> imageBBoxes = new ArrayList<Position> ();
    private List<ScaledImage> imageInstances = new ArrayList<ScaledImage> ();

    public int getNumberOfImages () { return this.imageInstances.size (); }

    public Position getBBoxForImage (int whichImage)
    {
	return this.imageBBoxes.get (whichImage);
    }

    public Rectangle scaleRectangleForImage (int whichImage, Rectangle in)
    {
	return this.getBBoxForImage (whichImage).scaleRectangle (in);
    }

    public ScaledImage getImage (int whichImage)
    {
	return this.imageInstances.get (whichImage);
    }

    public void setImage (int whichImage, ScaledImage image, Position bbox)
    {
	if (whichImage < this.imageInstances.size ())
	    {
		this.imageInstances.set (whichImage, image);
		this.imageBBoxes.set (whichImage, bbox);
	    }
	else
	    {
		this.imageInstances.add (whichImage, image);
		this.imageBBoxes.add (whichImage, bbox);
	    }
    }

    public boolean replaceImage (int whichImage, ScaledImage image)
    {
	if (whichImage < this.imageInstances.size ())
	    {
		if (this.imageInstances.get (whichImage) != image)
		    {
			this.imageInstances.set (whichImage, image);
			return true;
		    }
	    }
	return false;
    }

    public void clearImages ()
    {
	this.imageInstances.clear ();
	this.imageBBoxes.clear ();
    }
}
