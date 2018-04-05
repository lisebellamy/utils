package com.lisebellamy.util.coordlist;
import com.lisebellamy.util.MType;
import com.lisebellamy.util.Axis;

public class AspectRatio
    extends CoordList
{
    protected AspectRatio ()
    {
    }

    public String getName () { return "AspectRatio"; }
    public Axis[] getAxes () { return CoordList.TwoD.getAxes (); }

    public boolean isValidMType (MType type)
    {
	return (type.getType () == MType.Type.RatioXY
		&& (type.getAxis () == Axis.X || type.getAxis () == Axis.Y));
    }
    public int getNumCoords () { return 1; }

    public int getIndexForAxis (Axis axis)
    {
	if (axis == Axis.X || axis == Axis.Y)
	    {
		return 0;
	    }
	else
	    {
		return -1;
	    }
    }
}
