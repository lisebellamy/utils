package com.lisebellamy.util;
import java.awt.Point;
import java.awt.Dimension;
import com.lisebellamy.util.coordlist.TwoD;

public class StdWLocAndSize
    implements WLocAndSize
{
    WLoc loc = new StdWLoc ();
    WSize size = new StdWSize ();

    public StdWLocAndSize ()
    {
    }

    public StdWLocAndSize (int x, int y, int width, int height)
    {
	this.loc.set (x, y);
	this.size.set (TwoD.X, width);
	this.size.set (TwoD.Y, height);
    }

    public int getX () { return this.loc.get (TwoD.X); }
    public int getY () { return this.loc.get (TwoD.Y); }
    public int getWidth () { return this.size.get (TwoD.X); }
    public int getHeight () { return this.size.get (TwoD.Y); }

    public void setX (int value) { this.loc.set (TwoD.X, value); }
    public void setY (int value) { this.loc.set (TwoD.Y, value); }
    public void setWidth (int value) { this.size.set (TwoD.X, value); }
    public void setHeight (int value) { this.size.set (TwoD.Y, value); }

    public Point getLocAsPoint ()
    {
	return this.loc.getAsPoint ();
    }

    public Dimension getSizeAsDimension ()
    {
	return this.size.getAsDimension ();
    }

    public WLoc getLoc ()
    {
	return this.loc;
    }

    public WSize getSize ()
    {
	return this.size;
    }

    public void setLoc (WLoc locArg)
    {
	this.loc.set (locArg);
    }

    public void setLoc (Point locArg)
    {
	this.loc.set (locArg);
    }
    
    public void setSize (WSize sizeArg)
    {
	this.size.set (sizeArg);
    }

    public void setSize (Dimension sizeArg)
    {
	this.size.set (sizeArg);
    }
    
    public void addLocOffset (WSize size)
    {
	this.loc.add (size);
    }
    public void addSizeOffset (WSize size)
    {
	this.size.add (size);
    }
    public void subLocOffset (WSize size)
    {
	this.loc.sub (size);
    }
    public void subSizeOffset (WSize size)
    {
	this.size.sub (size);
    }
}
