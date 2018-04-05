package com.lisebellamy.util;
import java.awt.Point;
import java.awt.Dimension;
import com.lisebellamy.util.WLocAndSize;
import com.lisebellamy.util.PointOrDimension;

/**
 * A Measurement type.
 */
public interface MType
{
    public static enum Type
    {
	Position ("Position", 0),
	Distance ("Distance", 1),
	Fraction ("Fraction", 2),
	RatioXY ("Ratio", 3),
	Illegal ("Illegal", -1);
	
	private final String name;
	private final int typeInt;
	private Type (String nameArg, int typeIntArg)
	{
	    this.name = nameArg;
	    this.typeInt = typeIntArg;
	}

	public final String getName ()
	{
	    return this.name;
	}

	public final int getTypeInt ()
	{
	    return this.typeInt;
	}
    }
    
    public String getName ();
    /**
     * The type of measurement. 
     */
    MType.Type getType ();
    /**
     * The axis of this measurement.
     */
    Axis getAxis ();

    int getIndex ();
    
    public int getValue (Dimension d);
    public int getValue (Point p);
    public int getValue (WLocAndSize locAndSize);
    public Dimension setValue (Dimension d, int value);
    public Point setValue (Point p, int value);
    public void setValue (WLocAndSize locAndSize, int value);
    public int getValue (PointOrDimension pOrD);
    public void setValue (PointOrDimension pOrD, int value);
}
