package com.lisebellamy.util;
import java.util.Set;

/**
 * Interfaces to a set of subrectangles within an image.  The
 * rectangle coordinates are specified as fractions of the image width
 * and height.
 */
public interface ImageCoords
{
    public class NameExistsException
	extends Exception
    {
	public NameExistsException (String message)
	{
	    super (message);
	}
    }

    /**
     * Add a coordinate to this set.
     *
     * @param name the name of the coordinate
     * @param location the rectangle to describe
     * @throws NameExistsException if the coordinate name is already
     *         defined
     */
    public void addCoordinate (String name, FracRectangle location)
	throws NameExistsException;

    /**
     * Retrieves the named coordinate.
     * 
     * @param name the coordinate name
     * @returns the coordinate, or null if name is nonexistent
     */
    public FracRectangle getCoordForName (String name);

    /**
     * Returns the set of coordinate names.
     *
     * @returns the set of names
     */

    public Set<String> getCoordNames ();

    /**
     * Returns the size of the original image on which these coordinates
     * are based.
     * 
     * @returns the original image size, or null if not known
     */
    public WSize getSize ();
}
