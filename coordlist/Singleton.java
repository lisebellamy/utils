package com.lisebellamy.util.coordlist;
import com.lisebellamy.util.MType;
import com.lisebellamy.util.Axis;

public class Singleton
    extends CoordList
{
    private final Axis[] axisList = new Axis[1];

    protected Singleton (Axis axis)
    {
	this.axisList[0] = axis;
	CoordList.doSet (this, axis);
    }

    public String getName () { return "Singleton." + this.axisList[0].getName (); }
    public int getNumCoords () { return 1; }
    public boolean isValidMType (MType type)
    {
	MType.Type t = type.getType ();
	Axis axis = type.getAxis ();
	return (axis == this.axisList[0])
	    && (t == MType.Type.Distance
		|| t == MType.Type.Position
		|| t == MType.Type.Fraction);
    }
	   
    public Axis[] getAxes ()
    {
	return this.axisList;
    }

    public int getIndexForAxis (Axis axis)
    {
	if (axis == this.axisList[0])
	    {
		return 0;
	    }
	else
	    {
		return -1;
	    }
    }
}
