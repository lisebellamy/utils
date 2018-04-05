package com.lisebellamy.util;

import java.awt.Point;
import java.awt.Dimension;

public interface WLocAndSize
{
    public int getX ();
    public int getY ();
    public int getWidth ();
    public int getHeight ();

    public void setX (int x);
    public void setY (int y);
    public void setWidth (int width);
    public void setHeight (int height);
    
    public Point getLocAsPoint ();
    public Dimension getSizeAsDimension ();
    public WLoc getLoc ();
    public WSize getSize ();
    
    public void setLoc (WLoc loc);
    public void setLoc (Point loc);
    public void setSize (WSize size);

    public void addLocOffset (WSize size);
    public void addSizeOffset (WSize size);
    public void subLocOffset (WSize size);
    public void subSizeOffset (WSize size);
}
