package com.lisebellamy.util;

import java.awt.image.BufferedImage;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.Graphics;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.Date;
import java.awt.Color;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Paths;

public class GridImageOfImages
    implements GridImage
{
    private List<ScaledImage> imageList;
    private int numImages;
    private int width = -1;
    private int height = -1;

    public GridImageOfImages (MyControlFileReader reader,
			      ImageCache imageCache)
	throws IllegalArgumentException, java.io.IOException
    {
	this.imageList = new ArrayList<ScaledImage> ();
	String widthStr = reader.getNBLine ("");
	String heightStr = reader.getNBLine ("");
	if (widthStr == null || heightStr == null)
	    {
		throw new IllegalArgumentException ("File malformed--missing width and height");
	    }
	this.width = Integer.parseInt (widthStr);
	this.height = Integer.parseInt (heightStr);

	while (true)
	    {
		Color fillColor = null;
		String line = reader.getNBLine ("");
		if (line == null)
		    {
			break;
		    }
		String[] split = reader.getNBLine ("").split (",", -1);
		if (split[0].equals ("center"))
		    {
			if (split.length != 5)
			    {
				throw new IllegalArgumentException ("File malformed--need 5 paramters for center");
			    }
			int r = Integer.parseInt (split[1]);
			int g = Integer.parseInt (split[2]);
			int b = Integer.parseInt (split[3]);
			double a = Double.parseDouble (split[4]);
			fillColor = new Color (r, g, b, (int) (a * 255));
		    }
		String filename = reader.getNBLine ("");
		if (filename == null)
		    {
			throw new java.io.IOException ("Missing filename");
		    }
		ScaledImage image
		    = imageCache.readImage (filename, Paths.get (filename));
		if (image == null)
		    {
			throw new java.io.IOException ("Missing file " + filename);
		    }
		this.imageList.add (image);
	    }
    }

    public GridImageOfImages (List<ScaledImage> imageListArg)
    {
	this (imageListArg, -1, -1);
    }
    
    public GridImageOfImages (List<ScaledImage> imageListArg,
			      int widthArg,
			      int heightArg)
	throws IllegalArgumentException
    {
	this.width = widthArg;
	this.height = heightArg;
	
	int commonWidth = 0;
	int commonHeight = 0;
	
	this.numImages = imageListArg.size ();
	this.imageList = imageListArg;

	if (this.width < 0)
	    {
		for (int i = 0; i < this.numImages; i++)
		    {
			ScaledImage img = imageListArg.get (i);
			commonWidth = Math.max (commonWidth, img.getRawWidth ());
			commonHeight = Math.max (commonHeight, img.getRawHeight ());
		    }
		this.width = commonWidth;
		this.height = commonHeight;
	    }
    }

    public int getWidth ()
    {
	return this.width;
    }

    public int getHeight ()
    {
	return this.height;
    }

    public BufferedImage getImageForPosition (int pos)
    {
	return this.imageList.get (pos).getImage (this.width, this.height);
    }

    public int getNumberOfImages ()
    {
	return this.numImages;
    }
}
