package com.lisebellamy.util.coordlist;
import com.lisebellamy.util.MType;
import com.lisebellamy.util.Axis;

public abstract class CoordList
{
    private static final CoordList[] singletons
	= new CoordList[Axis.getNumAxis ()];

    public static final CoordList TwoD = new TwoD ();
    public static final CoordList SingletonX = new Singleton (Axis.X);
    public static final CoordList SingletonY = new Singleton (Axis.Y);
    public static final CoordList AspectRatio = new AspectRatio ();

    protected static void doSet (CoordList i, Axis axis)
    {
	CoordList.singletons[axis.getAxisNum ()] = i;
    }

    static public CoordList getSingleton (MType type)
    {
	if (type.getType () == MType.Type.RatioXY)
	    {
		return CoordList.AspectRatio;
	    }
	return CoordList.singletons[type.getAxis ().getAxisNum ()];
    }
    
    public abstract String getName ();
    public abstract Axis[] getAxes ();
    public abstract boolean isValidMType (MType type);
    public abstract int getNumCoords ();
    public abstract int getIndexForAxis (Axis axis);
}
