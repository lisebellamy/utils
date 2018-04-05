package com.lisebellamy.util;
import java.awt.Dimension;
import java.awt.Point;
import com.lisebellamy.util.coordlist.TwoD;

public class StdWLoc
    implements WLoc
{
    private final int values[] = new int[2];

    public StdWLoc ()
    {
    }

    public StdWLoc (int xArg, int yArg)
    {
	this.set (xArg, yArg);
    }

    public StdWLoc (WLoc loc)
    {
	for (Axis axis : TwoD.axes ())
	    {
		this.set (axis, loc.get (axis));
	    }
    }
    
    public StdWLoc (Point loc)
    {
	this.set (loc.x, loc.y);
    }
    
    public int get (Axis axis)
    {
	return this.values[axis.getAxisNum ()];
    }

    public void set (int xArg, int yArg)
    {
	this.values[TwoD.X.getAxisNum ()] = xArg;
	this.values[TwoD.Y.getAxisNum ()] = yArg;
    }

    public void set (Axis axis, int value)
    {
	this.values[axis.getAxisNum ()] = value;
    }

    public void set (WLoc locArg)
    {
	for (Axis axis : TwoD.axes ())
	    {
		this.values[axis.getAxisNum ()] = locArg.get (axis);
	    }
    }

    public void set (Point point)
    {
	this.set (point.x, point.y);
    }  
    
    public void add (WSize size)
    {
	for (Axis axis : TwoD.axes ())
	    {
		this.set (axis, this.get (axis) + size.get (axis));
	    }
    }
    
    public void sub (WSize size)
    {
	for (Axis axis : TwoD.axes ())
	    {
		this.set (axis, this.get (axis) - size.get (axis));
	    }
    }
    
    public Point getAsPoint ()
    {
	Point res = new Point (this.get (TwoD.X), this.get (TwoD.Y));
	return res;
    }
}

    
