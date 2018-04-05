package com.lisebellamy.util;
import java.awt.Dimension;

public interface WSize
{
    public int get (Axis axis);
    public void set (Axis axis, int value);
    public void set (Dimension size);
    public void set (WSize size);
    public void addValue (Axis axis, int value);
    public void mulValue (Axis axis, double fraction);
    public void add (WSize size);
    public void sub (WSize size);
    public Dimension getAsDimension ();
}
