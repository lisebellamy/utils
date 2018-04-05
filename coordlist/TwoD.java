package com.lisebellamy.util.coordlist;
import com.lisebellamy.util.MType;
import com.lisebellamy.util.Axis;

public class TwoD
    extends CoordList
{
    private static final Axis[] axes = { Axis.X, Axis.Y };
    private static final int[] indexForAxis = new int [Axis.getNumAxis ()];

    static {
	for (int n = 0; n < indexForAxis.length; n++)
	    {
		indexForAxis[n] = -1;
	    }
	int i = 0;
	for (Axis axis : axes)
	    {
		indexForAxis[axis.getAxisNum ()] = i;
		i++;
	    }
    }

    protected TwoD ()
    {
    }
    
    public String getName () { return "TwoD"; }
    public static Axis[] axes () { return axes; }
    public Axis[] getAxes () { return axes; }
    public int getNumCoords () { return axes.length; }
    public boolean isValidMType (MType mType)
    {
	Axis axis = mType.getAxis ();
	MType.Type type = mType.getType ();
	return (axis == Axis.X || axis == Axis.Y)
	    && (type == MType.Type.Distance
		|| type == MType.Type.Position
		|| type == MType.Type.Fraction);
    }
    public final static Axis X = Axis.X;
    public final static Axis Y = Axis.Y;

    public int getIndexForAxis (Axis axis)
    {
	return indexForAxis[axis.getAxisNum ()];
    }
}
