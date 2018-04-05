package com.lisebellamy.util;
import java.awt.Point;

public interface WLoc
{
    public int get (Axis axis);
    public void set (Axis axis, int value);
    public void set (int x, int y);
    
    public void add (WSize size);
    public void sub (WSize size);
    public Point getAsPoint ();
    public void set (Point location);
    public void set (WLoc location);
}
