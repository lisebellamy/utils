package com.lisebellamy.util;

public class StdFracRectangle
    implements FracRectangle
{
    final private double fracX;
    final private double fracY;
    final private double fracWidth;
    final private double fracHeight;
    
    public StdFracRectangle (double fracXArg, double fracYArg,
			     double fracWidthArg, double fracHeightArg)
    {
	this.fracX = fracXArg;
	this.fracY = fracYArg;
	this.fracWidth = fracWidthArg;
	this.fracHeight = fracHeightArg;
    }

    public double getFracX () { return this.fracX; }
    public double getFracY () { return this.fracY; }
    public double getFracWidth () { return this.fracWidth; }
    public double getFracHeight () { return this.fracHeight; }
}
