package com.lisebellamy.util;

import java.nio.file.Path;
import java.nio.file.Files;
import java.awt.Point;
import java.awt.Dimension;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.io.File;
import java.io.FileReader;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class StdImageCoords
    implements ImageCoords
{
    private final Map<String, FracRectangle> coordMap
	= new HashMap<String, FracRectangle> ();
    private final WSize size;

    private void readFile (Path path)
	throws java.io.IOException
    {
	FileReader in = new FileReader (path.toFile ());
	BufferedReader reader = new BufferedReader (in);
	int linenum = 0;
	while (true)
	    {
		String line = reader.readLine ();
		if (line == null)
		    {
			break;
		    }
		linenum++;
		int i = line.indexOf ("//");
		if (i >= 0)
		    {
			line = line.substring (0, i);
		    }
		line = line.trim ();
		if (line.length () == 0)
		    {
			continue;
		    }
		String[] args = line.split (",");
		if (args.length >= 3)
		    {
			String name = args[0].trim ();
			double x = this.parseCoord (args[1], this.getWidth ());
			double y = this.parseCoord (args[2], this.getHeight ());
			double width = 0.0;
			double height = 0.0;
			if (args.length == 5)
			    {
				width = this.parseCoord (args[3], this.getWidth ());
				height = this.parseCoord (args[4], this.getHeight ());
			    }
			FracRectangle r = new StdFracRectangle (x, y,
								width, height);
			try {
			    this.addCoordinate (name, r);
			} catch (ImageCoords.NameExistsException e)
			    {
				System.err.println ("At line " + linenum + " of file " + path.toString () + ", coordinate " + name + " previously defined");
			    }
		    }
	    }
	in.close ();
    }

    public StdImageCoords (WSize sizeArg)
    {
	assert sizeArg != null;
	this.size = sizeArg;
    }

    public StdImageCoords (Path imageFile, WSize sizeArg)
    {
	assert imageFile != null;
	this.size = sizeArg;
	String tryName = imageFile.getFileName () + ".crd";
	Path p = imageFile.resolveSibling (tryName);
	if (Files.exists (p))
	    {
		try {
		    this.readFile (p);
		} catch (Exception e)
		    {
		    }
	    }
    }

    public void addCoordinate (String name, FracRectangle location)
	throws ImageCoords.NameExistsException
    {
	assert name != null;
	assert location != null;
	if (this.coordMap.containsKey (name))
	    {
		throw new NameExistsException (name + " already defined");
	    }
	this.coordMap.put (name, location);
    }

    public FracRectangle getCoordForName (String name)
    {
	return this.coordMap.get (name);
    }

    public Set<String> getCoordNames ()
    {
	return this.coordMap.keySet ();
    }

    public WSize getSize ()
    {
	return this.size;
    }

    public int getWidth ()
    {
	if (this.size != null)
	    {
		return this.size.get (Axis.X);
	    }
	else
	    {
		return 0;
	    }
    }

    public int getHeight ()
    {
	if (this.size != null)
	    {
		return this.size.get (Axis.Y);
	    }
	else
	    {
		return 0;
	    }
    }

    private double parseCoord (String in, double widthOrHeight)
    {
	in = in.trim ();
	double val = Double.parseDouble (in);
	if (val > 1.0)
	    {
		val = val / widthOrHeight;
	    }
	return val;
    }
}

 
