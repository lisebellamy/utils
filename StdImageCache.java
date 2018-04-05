package com.lisebellamy.util;
import java.util.Map;
import java.util.HashMap;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public class StdImageCache
    implements ImageCache
{
    private FileSearch fileSearch;
    private Map<String, ScaledImage> imageCache
	= new HashMap<String, ScaledImage> ();
    private Map<String, ImageCoords> imageCoords
	= new HashMap<String, ImageCoords> ();

    public StdImageCache (FileSearch fileSearchArg)
    {
	this.fileSearch = fileSearchArg;
    }

    public boolean addImage (String name, Path file)
	throws java.io.IOException
    {
	Path fileToOpen = this.fileSearch.search (file);
	if (fileToOpen != null)
	    {
		ScaledImage image
		    = new StdScaledImage (ImageIO.read (fileToOpen.toFile ()));
		this.addImage (name, fileToOpen, image);
		return true;
	    }
	return false;
    }

    public void addImage (String name, Path file, ScaledImage image)
    {
	System.out.println ("FILE = " + file.toString ());
	if (! this.imageCache.containsKey (name))
	    {
		this.addImage (name, image, new StdImageCoords (file, new StdWSize (image.getRawWidth (), image.getRawHeight ())));
	    }
    }

    public void addImage (String name, ScaledImage image, ImageCoords coords)
    {
	if (! this.imageCache.containsKey (name))
	    {
		this.imageCache.put (name, image);
		this.imageCoords.put (name, coords);
	    }
    }

    public ScaledImage getScaledImage (String name)
    {
	return this.imageCache.get (name);
    }
    
    public ImageCoords getCoords (String name)
    {
	return this.imageCoords.get (name);
    }

    public ScaledImage readImage (String name, Path path)
	throws java.io.IOException
    {
	String s = path.toString ();
	if (this.imageCache.containsKey (name))
	    {
		return this.getScaledImage (name);
	    }
	if (this.addImage (name, path))
	    {
		return this.imageCache.get (name);
	    }
	else
	    {
		return null;
	    }
    }
}
