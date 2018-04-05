package com.lisebellamy.util;
import java.util.List;

/**
 * Represents an intersection of ranges of values between 0-127
 * inclusive.
 */
public interface MidiRange
{
    public interface DiscreteRange
    {
	public int getStart ();
	public int getEnd ();
	public boolean contains (int value);
    }
    /**
     * Adds a range of values.
     *
     * @param start start of the range
     * @param end end of the range
     */
    public void addRange (int start, int end);

    /**
     * Adds one number to the range.
     */
    public void addValue (int value);

    /**
     * Reads in a set of ranges.
     *
     * @param data node to read from
     */
    public void readFrom (StructuredData data);

    /**
     * Writes a representation of the ranges represented by this
     * instance.
     *
     * @param data node to write to
     */
    public void writeInto (StructuredData data);

    /**
     * Returns a String representation of this range in the form:
     *
     * {value | value '-' value}*
     * 
     * The values are separated by commas.
     * 
     * An empty string is returned if this range is empty.
     *
     * @returns the string as described
     */

    public String getStringRep ();

    /**
     * @returns a list of DiscreteRange objects representing this range
     */
    public List<DiscreteRange> getDiscreteRangeRep ();

    /**
     * Adds the given range to this range.
     *
     * @param range the range to add
     */
    public void addRange (DiscreteRange range);

    /**
     * Clears this range.
     */
    public void clear ();

    /**
     * Tests if a value occurs in this range.
     *
     * @param value the value to be tested
     * @returns true if value is in this range.
     */
    public boolean contains (int value);
}
