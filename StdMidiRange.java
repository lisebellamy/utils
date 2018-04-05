package com.lisebellamy.util;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import org.codehaus.janino.SimpleCompiler;

public class StdMidiRange
    implements MidiRange
{
    private List<StdDiscreteRange> ranges
	= new ArrayList<StdDiscreteRange> ();
    private boolean finalized = false;
    private static int comparatorCount = 0;

    public interface RangeComparator
    {
	public boolean contains (int value);
    }

    private RangeComparator myComparator = null;
    
    private class NeedsComparator
	implements RangeComparator
    {
	public boolean contains (int value)
	{
	    StdMidiRange.this.generateComparator ();
	    return StdMidiRange.this.myComparator.contains (value);
	}
    }

    public void clear ()
    {
	this.ranges.clear ();
	this.finalized = false;
	this.myComparator = new NeedsComparator ();
    }

    public List<DiscreteRange> getDiscreteRangeRep ()
    {
	this.optimizeRanges ();
	List<DiscreteRange> res = new ArrayList<DiscreteRange> ();
	for (DiscreteRange r : this.ranges)
	    {
		res.add (r);
	    }
	return res;
    }

    public String getStringRep ()
    {
	this.optimizeRanges ();
	StringBuilder res = new StringBuilder ();
	for (DiscreteRange range : this.ranges)
	    {
		if (res.length () > 0)
		    {
			res.append (", ");
		    }
		if (range.getStart () == range.getEnd ())
		    {
			res.append (Integer.toString (range.getStart ()));
		    }
		else
		    {
			res.append (range.getStart () + " - " + range.getEnd ());
		    }
	    }
	return res.toString ();
    }

    private RangeComparator compileComparator (String expression)
    {
	return this.compileComparator (null, null, expression);
    }
    
    private RangeComparator compileComparator (String members, String init,
					       String expression)
    {
	String className = "CompiledComparator" + comparatorCount;
	comparatorCount++;
	String classStr = "package com.lisebellamy.util;\npublic class " + className + " implements StdMidiRange.RangeComparator { ";
	if (members != null)
	    {
		classStr += members;
	    }
	if (init != null)
	    {
		classStr += "public " + className + " () { " + init + " }";
	    }
	classStr += "public boolean contains (int value) { return " + expression + "; }";
	classStr += " }";

	System.out.println (classStr);
	try {
	    SimpleCompiler compiler = new SimpleCompiler ();
	    compiler.cook (classStr);

	    Class<?> clazz
		= Class.forName ("com.lisebellamy.util." + className, true,
				 compiler.getClassLoader ());

	    return (RangeComparator) clazz.newInstance();
	} catch (Exception e)
	    {
		e.printStackTrace ();
		return null;
	    }
    }
    
    public static class StdDiscreteRange
	implements MidiRange.DiscreteRange, Comparable<StdDiscreteRange>
    {
	private final int start;
	private final int end;

	public StdDiscreteRange (int startArg, int endArg)
	{
	    this.start = startArg;
	    this.end = endArg;
	}

	public StdDiscreteRange (String rep)
	{
	    String[] args = rep.split ("-");
	    if (args.length > 1)
		{
		    this.start = Integer.parseInt (args[0]);
		    this.end = Integer.parseInt (args[1]);
		}
	    else
		{
		    this.start = Integer.parseInt (rep);
		    this.end = this.start;
		}
	}

	public int getStart () { return this.start; }
	public int getEnd () { return this.end; }

	public void setFlags (boolean[] flags)
	{
	    for (int x = this.start; x <= this.end; x++)
		{
		    flags[x] = true;
		}
	}

	public int compareTo (StdDiscreteRange r)
	{
	    int rStart = r.getStart ();
	    int thisStart =  this.start;
	    if (rStart < thisStart)
		{
		    return 1;
		}
	    else if (rStart == thisStart)
		{
		    int rEnd = r.getEnd ();
		    int thisEnd = this.end;
		    
		    if (rEnd < thisEnd)
			{
			    return 1;
			}
		    else if (rEnd == thisEnd)
			{
			    return 0;
			}
		    else
			{
			    return -1;
			}
		}
	    else
		{
		    return -1;
		}
	}

	public final boolean intersects (int rStart, int rEnd)
	{
	    if (rStart <= (this.start - 1))
		{
		    return rEnd >= (this.start - 1);
		}
	    else
		{
		    return rStart <= (this.end + 1);
		}
	}

	public final boolean intersects (StdDiscreteRange r)
	{
	    return this.intersects (r.start, r.end);
	}

	public boolean contains (int value)
	{
	    return this.start <= value && this.end >= value;
	}

	public String getStringRep ()
	{
	    String rep = Integer.toString (this.start);
	    if (this.start != this.end)
		{
		    rep = rep + "-" + this.end;
		}
	    return rep;
	}
    }

    public void addRange (int start, int end)
    {
	this.addRange (new StdDiscreteRange (start, end));
    }

    public void addRange (DiscreteRange newRange)
    {
	this.addRange (new StdDiscreteRange (newRange.getStart (),
					     newRange.getEnd ()));
    }
    
    public void addRange (StdDiscreteRange newRange)
    {
	this.finalized = false;
	this.myComparator = new NeedsComparator ();
	this.ranges.add (newRange);
    }

    public void addValue (int value)
    {
	this.addRange (new StdDiscreteRange (value, value));
    }

    private static int max (int i1, int i2)
    {
	return (i1 > i2) ? i1 : i2;
    }

    private void optimizeRanges ()
    {
	if (this.finalized)
	    {
		return;
	    }
	Collections.sort (this.ranges);
	List<StdDiscreteRange> rangeList = this.ranges;
	this.ranges = new ArrayList<StdDiscreteRange> ();
	int rangeListLength = rangeList.size ();
	int x = 0;
	while (x < rangeListLength)
	    {
		StdDiscreteRange currRange = rangeList.get (x);
		int min = currRange.getStart ();
		int max = currRange.getEnd ();
		StdDiscreteRange lastIntersect = currRange;
		int y = x + 1;
		for (; y < rangeListLength; y++)
		    {
			StdDiscreteRange nextRange = rangeList.get (y);
			if (nextRange.intersects (min, max))
			    {
				lastIntersect = nextRange;
				max = StdMidiRange.max (max,
							nextRange.getEnd ());
			    }
			else
			    {
				break;
			    }
		    }
		if (lastIntersect.getEnd () != max)
		    {
			currRange
			    = new StdDiscreteRange (currRange.getStart (),
						    max);
		    }
		this.ranges.add (currRange);
		x = y;
	    }
	this.finalized = true;
    }

    public RangeComparator makeSingleComparator (DiscreteRange range)
    {
	if (range.getStart () == range.getEnd ())
	    {
		return this.compileComparator ("value == " + range.getStart ());
	    }
	else
	    {
		return this.compileComparator ("(value >= " + range.getStart () + ") && (value <= " + range.getEnd () + ")");
	    }
    }

    private String makeMultipleComparator (List<DiscreteRange> ranges,
					   boolean hasLowerBound,
					   int lowerBound,
					   boolean hasUpperBound,
					   int upperBound)
    {
	if (ranges.size () == 0)
	    {
		return "false";
	    }
	StringBuilder res = new StringBuilder ();
	res.append ("(");
	if (ranges.size () == 1)
	    {
		boolean append = false;
		DiscreteRange range = ranges.get (0);
		if ((! hasLowerBound) || (lowerBound != range.getStart ()))
		    {
			append = true;
			res.append ("value >= " + range.getStart ());
		    }
		if ((! hasUpperBound) || (upperBound != range.getEnd ()))
		    {
			if (append)
			    {
				res.append (" && ");
			    }
			else
			    {
				append = true;
			    }
			res.append ("value <= " + range.getEnd ());
		    }
		if (! append)
		    {
			res.append ("true");
		    }
		res.append (")");
		return res.toString ();
	    }
	int place = ranges.size () / 2;
	DiscreteRange range = ranges.get (place);
	if ((! hasUpperBound) || upperBound != range.getEnd ())
	    {
		res.append ("value <= " + range.getEnd () + " ? ");
		res.append (this.makeMultipleComparator (ranges.subList (0, place + 1),
							 hasLowerBound,
							 lowerBound,
							 true,
							 range.getEnd ()));
		res.append (" : ");
		if ((place + 1) < ranges.size ())
		    {
			res.append (this.makeMultipleComparator (ranges.subList (place + 1, ranges.size ()),
								 true,
								 range.getEnd () + 1,
								 hasUpperBound,
								 upperBound));
		    }
		else
		    {
			res.append ("false");
		    }
	    }
	else
	    {
		res.append ("value >= " + range.getStart () + " ? ");
		res.append (this.makeMultipleComparator (ranges.subList (place, ranges.size ()),
							 true,
							 range.getStart (),
							 hasUpperBound,
							 upperBound));
		res.append (" : ");
		if (place > 0)
		    {
			res.append (this.makeMultipleComparator (ranges.subList (0, place),
								 hasLowerBound,
								 lowerBound,
								 true,
								 range.getStart () - 1));
		    }
		else
		    {
			res.append ("false");
		    }
	    }
	res.append (")");
	return res.toString ();
    }

    public RangeComparator makeMultipleComparator (List<DiscreteRange> ranges)
    {
	String expr = this.makeMultipleComparator (ranges,
						   false, 0,
						   false, 0);
	return this.compileComparator (expr);
    }

    private class ArrayComparator
    {
	final int start;
	final int end;
	final String members;
	final String init;
	final String expr;
	final String flags;

	ArrayComparator (int startArg, int endArg, String membersArg,
			 String initArg, String exprArg, String flagsArg)
	{
	    this.start = startArg;
	    this.end = endArg;
	    this.members = membersArg;
	    this.init = initArg;
	    this.expr = exprArg;
	    this.flags = flagsArg;
	}
    }

    public ArrayComparator genArrayComparator (String suffix,
					       List<DiscreteRange> ranges,
					       boolean skipUpperBound)
    {
	int first = ranges.get (0).getStart ();
	int last = ranges.get (ranges.size () - 1).getEnd ();
	String members = "boolean flags" + suffix + "[] = new boolean[" + (last - first + 1) + "];";
	StringBuilder init = new StringBuilder ();
	String flags = "this.flags" + suffix;
	for (DiscreteRange range : ranges)
	    {
		if (range.getStart () == range.getEnd ())
		    {
			init.append (flags + "[" + (range.getStart () - first) + "] = true;");
		    }
		else
		    {
			init.append ("for (int x = " + range.getStart () + "; x <= " + range.getEnd () + "; x++) { " + flags + "[x - " + first + "] = true; }");
		    }
	    }
	String expr = "(value >= " + first + " && value <= " + last + " && " + flags + "[value - " + first + "])";
	return new ArrayComparator (first, last, members, init.toString (),
				    expr, flags);
    }
    
    public RangeComparator makeArrayComparator (List<DiscreteRange> ranges)
    {
	ArrayComparator res = this.genArrayComparator ("0", ranges, false);
	return this.compileComparator (res.members, res.init, res.expr);
    }

    private String recursiveProcess (List<ArrayComparator> comps,
				     boolean hasLowerBound,
				     int lowerBound,
				     boolean hasUpperBound,
				     int upperBound)
    {
	StringBuilder res = new StringBuilder ();
	res.append ("(");
	if (comps == null || comps.size () == 0)
	    {
		res.append ("false)");
		return res.toString ();
	    }
	if (comps.size () == 1)
	    {
		ArrayComparator comp = comps.get (0);

		assert ((! hasLowerBound) || lowerBound <= comp.start);
		assert ((! hasUpperBound) || upperBound >= comp.end);
		if ((! hasLowerBound) || lowerBound != comp.start)
		    {
			res.append ("value >= " + comp.start + " && ");
		    }
		if ((! hasUpperBound) || upperBound != comp.end)
		    {
			res.append ("value <= " + comp.end + " && ");
		    }
		res.append (comp.flags + "[value - " + comp.start + "])");
		return res.toString ();
	    }
	int place = comps.size () / 2;
	ArrayComparator comp = comps.get (place);
	if (! hasUpperBound || upperBound != comp.end)
	    {
		res.append ("(value <= " + comp.end + " ? ");
		res.append (this.recursiveProcess (comps.subList (0, place + 1),
						   hasLowerBound,
						   lowerBound,
						   true,
						   comp.end));
		res.append (" : ");
		if ((place + 1) < comps.size ())
		    {
			res.append (this.recursiveProcess (comps.subList (place + 1, comps.size ()),
							   true,
							   comp.end + 1,
							   hasUpperBound,
							   upperBound));
		    }
		else
		    {
			res.append (" false");
		    }
		res.append (")");
	    }
	else
	    {
		res.append ("(value >= " + comp.start + " ? ");
		res.append (this.recursiveProcess (comps.subList (place, comps.size ()),
						   true,
						   comp.start,
						   hasUpperBound,
						   upperBound));
		res.append (" : ");
		if (place > 0)
		    {
			res.append (this.recursiveProcess (comps.subList (0, place),
							   hasLowerBound,
							   lowerBound,
							   true,
							   comp.start - 1));
		    }
		else
		    {
			res.append (" false");
		    }
		res.append (")");
	    }
	res.append (")");
	return res.toString ();
    }

    public RangeComparator makeMultiArrayComparator (List<List<DiscreteRange>> ranges)
    {
	ArrayList<ArrayComparator> comps = new ArrayList<ArrayComparator> ();
	String members = "";
	String init = "";
	for (List<DiscreteRange> singleRangeList : ranges)
	    {
		String suffix = Integer.toString (comps.size ());
		ArrayComparator comp = this.genArrayComparator (suffix,
								singleRangeList,
								true);
		comps.add (comp);
		members += comp.members + "\n";
		init += comp.init + "\n";
	    }
	String expr = this.recursiveProcess (comps, false, 0, false, 0);
	return this.compileComparator (members, init, expr);
    }

    private class StdComparator
	implements RangeComparator
    {
	public boolean contains (int value)
	{
	    if (StdMidiRange.this.ranges.size () == 0)
		{
		    return false;
		}
	    int lower = 0;
	    int upper = StdMidiRange.this.ranges.size ();
	    while (lower < upper)
		{
		    int place = (lower + upper) / 2;
		    DiscreteRange r = StdMidiRange.this.ranges.get (place);
		    int rStart = r.getStart ();
		    if (rStart > value)
			{
			    upper = place - 1;
			}
		    else if (rStart < value)
			{
			    if (r.getEnd () >= value)
				{
				    return true;
				}
			    lower = place + 1;
			}
		    else
			{
			    return true;
			}
		}
	    int place = (lower + upper) / 2;
	    if (place < StdMidiRange.this.ranges.size ())
		{
		    DiscreteRange r = StdMidiRange.this.ranges.get (place);
		    return r.contains (value);
		}
	    return false;
	}
    }
    
    private void generateComparator ()
    {
	if (! this.finalized)
	    {
		this.optimizeRanges ();
	    }
	
	if (this.ranges.size () == 1)
	    {
		this.myComparator = this.makeSingleComparator (this.ranges.get (0));
	    }
	else
	    {
		this.myComparator = new StdComparator ();
	    }
    }

    public boolean contains (int value)
    {
	return this.myComparator.contains (value);
    }

    public void writeInto (StructuredData node)
    {
	this.optimizeRanges ();
	for (StdDiscreteRange range : this.ranges)
	    {
		node.addData (range.getStringRep ());
	    }
    }
    
    public void readFrom (StructuredData node)
    {
	for (String ent : node.getData ())
	    {
		this.addRange (new StdDiscreteRange (ent));
	    }
    }
}
