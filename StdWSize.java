package com.lisebellamy.util;
import java.awt.Dimension;
import java.awt.Point;
import com.lisebellamy.util.coordlist.TwoD;
import com.lisebellamy.util.coordlist.CoordList;

public class StdWSize
    implements WSize
{
    private int[] sizes = new int[2];

    public StdWSize (int widthArg, int heightArg)
    {
	this.set (widthArg, heightArg);
    }

    public StdWSize (WSize size)
    {
	this.sizes[TwoD.X.getAxisNum ()]  = size.get (TwoD.X);
	this.sizes[TwoD.Y.getAxisNum ()]  = size.get (TwoD.Y);
    }

    public StdWSize (Dimension dim)
    {
	this (dim.width, dim.height);
    }

    public StdWSize ()
    {
	this.sizes[0] = 0;
	this.sizes[1] = 0;
    }
    
    public int get (Axis axis)
    {
	return this.sizes[axis.getAxisNum ()];
    }
    
    public void set (Axis axis, int value)
    {
	this.sizes[axis.getAxisNum ()] = value;
    }
    
    public void set (int widthArg, int heightArg)
    {
	this.sizes[TwoD.X.getAxisNum ()]  = widthArg;
	this.sizes[TwoD.Y.getAxisNum ()]  = heightArg;
    }
    
    public void add (WSize size)
    {
	for (Axis axis : CoordList.TwoD.getAxes ())
	    {
		this.sizes[axis.getAxisNum ()] += size.get (axis);
	    }
    }

    public void addValue (Axis axis, int value)
    {
	this.sizes[axis.getAxisNum ()] += value;
    }
    
    public void mulValue (Axis axis, double fraction)
    {
	this.sizes[axis.getAxisNum ()] *= fraction;
    }
    
    public void sub (WSize size)
    {
	for (Axis axis : CoordList.TwoD.getAxes ())
	    {
		this.sizes[axis.getAxisNum ()] -= size.get (axis);
	    }
    }
    
    public Dimension getAsDimension ()
    {
	Dimension res
	    = new Dimension (this.sizes[TwoD.X.getAxisNum ()],
			     this.sizes[TwoD.Y.getAxisNum ()]);
	return res;
    }

    public void set (Dimension d)
    {
	this.set (d.width, d.height);
    }

    public void set (WSize size)
    {
	for (Axis axis : CoordList.TwoD.getAxes ())
	    {
		this.sizes[axis.getAxisNum ()] = size.get (axis);
	    }
    }
}

    
