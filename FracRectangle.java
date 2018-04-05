package com.lisebellamy.util;

/** 
 * Represents a rectangle which is a fraction of a surrounding
 * rectangle. Fractions range from 0 to 1.0d inclusive.
 */
public interface FracRectangle
{
    /**
     * Return the fractional location on the X axis.
     *
     * @returns y fraction
     */
    double getFracX ();
    
    /**
     * Return the fractional location on the Y axis.
     *
     * @returns y fraction
     */
    double getFracY ();
    
    /**
     * Return the fractional width of the rectangle.
     *
     * @returns width
     */
    double getFracWidth ();
    
    /**
     * Return the fractional height of the rectangle.
     * 
     * @returns height
     */
    double getFracHeight ();
}
