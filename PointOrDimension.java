package com.lisebellamy.util;
import java.awt.Point;
import java.awt.Dimension;

public class PointOrDimension
{
    private Point point;
    private Dimension dimension;
    
    public PointOrDimension (Point p)
    {
	this.point = p;
	this.dimension = null;
    }
    public PointOrDimension (Dimension d)
    {
	this.dimension = d;
	this.point = null;
    }

    public PointOrDimension (Object o)
    {
	if (o instanceof Point)
	    {
		this.point = (Point) o;
		this.dimension = null;
	    }
	else if (o instanceof Dimension)
	    {
		this.dimension = (Dimension) o;
		this.point = null;
	    }
	else
	    {
		throw new Error ("Object must be either Dimension or Point in PointOrDimension constructor");
	    }
    }

    public boolean isPoint () { return this.point != null; }
    public boolean isDimension () { return this.dimension != null; }
    public Point getPoint () { return this.point; }
    public Dimension getDimension () { return this.dimension; }
    public void setValue (Point p) {
	assert this.point != null;
	this.point = p;
    }
    public void setValue (Dimension d) {
	assert this.dimension != null;
	this.dimension = d;
    }
    public void setValue (Object o)
    {
	assert (o instanceof Point) || (o instanceof Dimension);
	if (o instanceof Point)
	    {
		this.setValue ((Point) o);
	    }
	else
	    {
		this.setValue ((Dimension) o);
	    }
    }
	    
    public Object getAsObject ()
    {
	if (this.point != null)
	    {
		return this.point;
	    }
	else
	    {
		return this.dimension;
	    }
    }
}
