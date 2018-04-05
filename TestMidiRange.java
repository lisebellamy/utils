package com.lisebellamy.util;

public class TestMidiRange
{
    public static void main (String[] args)
    {
	MidiRange r = new StdMidiRange ();
	boolean testValues = false;
	for (String arg : args)
	    {
		if (arg.equals (":"))
		    {
			testValues = true;
			System.out.println (r.getStringRep ());
			continue;
		    }
		if (testValues)
		    {
			System.out.println (r.contains (Integer.parseInt (arg)));
			continue;
		    }
		r.addRange (new StdMidiRange.StdDiscreteRange (arg));
	    }
    }
}
