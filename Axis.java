package com.lisebellamy.util;
import java.awt.Dimension;
import java.awt.Point;
import com.lisebellamy.util.WLoc;
import com.lisebellamy.util.WSize;

public abstract class Axis
{
    public final static Axis X = new AxisX ();
    public final static Axis Y = new AxisY ();
    public final static Axis Illegal = new AxisIllegal ();

    public static int getNumAxis () { return 3; }

    public abstract String getName ();
    public abstract int getAxisNum ();
    public abstract MType getLocType ();
    public abstract MType getSizeType ();
    public abstract MType getConstType ();
    public abstract boolean isCoord ();

    private static class AxisX
	extends Axis
    {
	public String getName () { return "x"; }
	public int getAxisNum () { return 0; }
	public MType getLocType () { return StdMType.XPos; }
	public MType getSizeType () { return StdMType.Width; }
	public MType getConstType () { return StdMType.FractionX; }
	public boolean isCoord () { return true; }
    }

    private static class AxisY
	extends Axis
    {
	public String getName () { return "y"; }
	public int getAxisNum () { return 1; }
	public MType getLocType () { return StdMType.YPos; }
	public MType getSizeType () { return StdMType.Height; }
	public MType getConstType () { return StdMType.FractionY; }
	public boolean isCoord () { return true; }
    }

    private static class AxisIllegal
	extends Axis
    {
	public String getName () { return "Illegal"; }
	public int getAxisNum () { return -1; }
	public MType getLocType () { return null; }
	public MType getSizeType () { return null; }
	public MType getConstType () { return null; }
	public boolean isCoord () { return false; }
    }
}
