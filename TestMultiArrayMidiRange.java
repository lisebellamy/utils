package com.lisebellamy.util;
import java.util.List;
import java.util.ArrayList;

public class TestMultiArrayMidiRange
{
    public static void main (String[] args)
    {
	ArrayList<List<MidiRange.DiscreteRange>> totalList = new ArrayList<List<MidiRange.DiscreteRange>> ();
	boolean testValues = false;
	StdMidiRange currentRange = new StdMidiRange ();
	StdMidiRange.RangeComparator rc = null;
	for (String arg : args)
	    {
		if (arg.equals ("-"))
		    {
			totalList.add (currentRange.getDiscreteRangeRep ());
			currentRange.clear ();
			continue;
		    }
		if (arg.equals ("mc"))
		    {
			testValues = true;
			rc = currentRange.makeMultipleComparator (currentRange.getDiscreteRangeRep ());
			continue;
		    }
		if (arg.equals (":"))
		    {
			testValues = true;
			StdMidiRange r = new StdMidiRange ();
			rc = r.makeMultiArrayComparator (totalList);
			continue;
		    }
		if (testValues)
		    {
			System.out.println (rc.contains (Integer.parseInt (arg)));
			continue;
		    }
		currentRange.addRange (new StdMidiRange.StdDiscreteRange (arg));
	    }
    }
}
