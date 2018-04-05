package com.lisebellamy.util;
import java.awt.Point;
import java.awt.Dimension;
import java.util.List;
import java.util.ArrayList;
import com.lisebellamy.util.WLocAndSize;
import com.lisebellamy.util.PointOrDimension;

public class StdMType
    implements MType
{
    private final MType.Type type;
    private final String name;
    private final Axis axis;
    private final int index;
    static final private List<StdMType> typeList = new ArrayList<StdMType> ();

    private StdMType (String nameArg, MType.Type typeArg, Axis axisArg)
    {
	this.name = nameArg;
	this.type = typeArg;
	this.axis = axisArg;
	this.index = StdMType.typeList.size ();
	StdMType.typeList.add (this);
    }

    public static StdMType findType (MType.Type type, Axis axis)
    {
	for (StdMType t : StdMType.typeList)
	    {
		if (t.getType () == type && t.getAxis () == axis)
		    {
			return t;
		    }
	    }
	return null;
    }

    public static StdMType findType (String name)
    {
	for (StdMType t : StdMType.typeList)
	    {
		if (name.equals (t.getName ()))
		    {
			return t;
		    }
	    }
	return null;
    }

    public static int getNumTypes () { return StdMType.typeList.size (); }

    public final MType.Type getType () { return this.type; }
    public final Axis getAxis () { return this.axis; }
    public final int getIndex () { return this.index; }

    public String getName ()
    {
	return this.name;
    }
    
    public int getValue (Dimension d)
    {
	assert this.type == MType.Type.Distance;
	if (this.axis == Axis.X)
	    {
		return (int) d.getWidth ();
	    }
	else if (this.axis == Axis.Y)
	    {
		return (int) d.getHeight ();
	    }
	throw new IllegalArgumentException ("Axis is not X or Y");
    }

    public int getValue (Point p)
    {
	assert this.type == MType.Type.Position;
	if (this.axis == Axis.X)
	    {
		return (int) p.getX ();
	    }
	else if (this.axis == Axis.Y)
	    {
		return (int) p.getY ();
	    }
	throw new IllegalArgumentException ("Axis is not X or Y");
    }
    
    public Dimension setValue (Dimension d, int value)
    {
	assert this.type == MType.Type.Distance;
	if (this.axis == Axis.X)
	    {
		d.setSize (value, d.getHeight ());
		return d;
	    }
	else if (this.axis == Axis.Y)
	    {
		d.setSize (d.getWidth (), value);
		return d;
	    }
	throw new IllegalArgumentException ("Axis is not X or Y");
    }

    public Point setValue (Point p, int value)
    {
	assert this.type == MType.Type.Position;
	if (this.axis == Axis.X)
	    {
		p.setLocation (value, p.getY ());
		return p;
	    }
	else if (this.axis == Axis.Y)
	    {
		p.setLocation (p.getX (), value);
		return p;
	    }
	throw new IllegalArgumentException ("Axis is not X or Y");
    }

    public void setValue (WLocAndSize loc, int value)
    {
	assert this.type == MType.Type.Position
	    || this.type == MType.Type.Distance;
	if (this.type == MType.Type.Position)
	    {
		loc.getLoc ().set (this.getAxis (), value);
	    }
	else
	    {
		loc.getSize ().set (this.getAxis (), value);
	    }
    }

    public int getValue (PointOrDimension pOrD)
    {
	assert this.type == MType.Type.Position
	    || this.type == MType.Type.Distance;
	if (this.type == MType.Type.Position)
	    {
		Point p = pOrD.getPoint ();
		assert p != null;
		return this.getValue (p);
	    }
	else
	    {
		Dimension d = pOrD.getDimension ();
		assert d != null;
		return this.getValue (d);
	    }
    }


    public int getValue (WLocAndSize loc)
    {
	assert this.type == MType.Type.Position
	    || this.type == MType.Type.Distance;
	if (this.type == MType.Type.Position)
	    {
		return loc.getLoc ().get (this.getAxis ());
	    }
	else
	    {
		return loc.getSize ().get (this.getAxis ());
	    }
    }
    
    public void setValue (PointOrDimension pOrD, int value)
    {
	assert this.type == MType.Type.Position
	    || this.type == MType.Type.Distance;
	if (this.type == MType.Type.Position)
	    {
		Point p = pOrD.getPoint ();
		assert p != null;
		p = this.setValue (p, value);
		pOrD.setValue (p);
	    }
	else
	    {
		Dimension d = pOrD.getDimension ();
		assert d != null;
		d = this.setValue (d, value);
		pOrD.setValue (d);
	    }
    }
    
    static public final MType FractionX
	= new StdMType ("ratioX", MType.Type.Fraction, Axis.X);
    static public final MType FractionY
	= new StdMType ("ratioY", MType.Type.Fraction, Axis.Y);
    static public final MType XPos
	= new StdMType ("xPos", MType.Type.Position, Axis.X);
    static public final MType YPos
	= new StdMType ("yPos", MType.Type.Position, Axis.Y);
    static public final MType Width
	= new StdMType ("width", MType.Type.Distance, Axis.X);
    static public final MType Height
	= new StdMType ("height", MType.Type.Distance, Axis.Y);
    static public final MType AspectRatioXY
	= new StdMType ("aspectRatio", MType.Type.RatioXY, Axis.X);
    static public final MType Illegal
	= new StdMType ("illegal", MType.Type.Illegal, Axis.Illegal);
}
