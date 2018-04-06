package com.lisebellamy.util;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Set;
import java.io.BufferedWriter;
import java.io.BufferedReader;

/* The data storage format:

   nodename {
     nodename {
       ;dataa
       ;datab
     }
   }

A packed representation:
nodename{nodename{;dataa
;datab
}}

It is designed to be easily edited by users when dealing with text.

Nodenames may be nested any number of levels.

Whitespace is optional.

Nodenames are from the set [0-9A-Za-z_].

A semicolon marks the start of data, and everything up to the newline
is considered part of the data. Subsequent semicolons append a newline
to the data.

This uses *newline*, not the system EOL. Arbitrary choice which avoids
quoting (assuming sending binary data is not an issue).

Multiple data chunks may be associated with a nodename:
  nodename {
    {
      ;d1a
      ;d1b
    }
    {
      ;d2a
      ;d2b
    }
  }

Data nodes are nameless. If only one data chunk is present, the braces
are optional.
*/

   
public class StdStructuredData
    implements StructuredData
{
    private String name;
    private List<String> ourData = null;
    private HashMap<String, StdStructuredData> children = null;

    public StdStructuredData (String nameArg)
    {
	this.name = nameArg;
    }

    public StdStructuredData ()
    {
    }

    public StdStructuredData (BufferedReader in)
	throws java.io.IOException
    {
	this.readFrom (in);
    }

    public String getName ()
    {
	return this.name;
    }
    
    public boolean isDataNode ()
    {
	return this.ourData != null;
    }

    public List<String> getData ()
    {
	return this.ourData;
    }

    public String getData (String childName)
    {
	StructuredData d = this.getChild (childName);
	if (d == null)
	    {
		return null;
	    }
	if (d.getData () == null)
	    {
		return null;
	    }
	return d.getData ().get (0);
    }

    public boolean hasMultipleData ()
    {
	return this.ourData != null && this.ourData.size () > 1;
    }

    public void setData (String data)
    {
	if (this.hasMultipleData ())
	    {
		throw new IllegalArgumentException ("setData () invoked on node " + this.name + " and node has multiple data");
	    }
	if (this.children != null)
	    {
		throw new IllegalArgumentException ("setData () invoked on node " + this.name + " and node has children");
	    }
	if (this.ourData == null)
	    {
		this.ourData = new ArrayList<String> ();
	    }
	this.ourData.clear ();
	this.ourData.add (data);
    }

    public void setData (String childName, String data)
    {
	StructuredData d = this.addOrReturnChild (childName);
	d.setData (data);
    }

    public void addData (String data)
    {
	if (this.children != null)
	    {
		throw new IllegalArgumentException ("addData () invoked on node " + this.name + " and node has children");
	    }
	if (this.ourData == null)
	    {
		this.ourData = new ArrayList<String> ();
	    }
	this.ourData.add (data);
    }

    public void addData (String childName, String data)
    {
	StructuredData d = this.addOrReturnChild (childName);
	d.addData (data);
    }

    public Set<String> getChildNames ()
    {
	if (this.ourData != null || this.children == null)
	    {
		return null;
	    }
	return this.children.keySet ();
    }

    public StructuredData getChild (String childName)
    {
	if (this.ourData != null)
	    {
		throw new IllegalArgumentException ("getChild () invoked on node " + this.name + " and node contains data");
	    }
	if (this.children == null)
	    {
		return null;
	    }
	return this.children.get (childName);
    }

    public void addChild (StdStructuredData d)
    {
	if (this.children == null)
	    {
		this.children = new HashMap<String, StdStructuredData> ();
	    }
	this.children.put (d.getName (), d);
    }

    private StdStructuredData doAddOrReturnChild (String childName)
    {
	if (this.ourData != null)
	    {
		throw new IllegalArgumentException ("addOrReturnChild () invoked on node " + this.name + " with child name " + childName + " and node contains data");
	    }
	if (this.children == null)
	    {
		this.children = new HashMap<String, StdStructuredData> ();
	    }
	StdStructuredData res = this.children.get (childName);
	if (res == null)
	    {
		res = new StdStructuredData (childName);
		this.children.put (childName, res);
	    }
	return res;
    }
    
    public StructuredData addOrReturnChild (String childName)
    {
	return this.doAddOrReturnChild (childName);
    }

    private static String genPrefix (int depth)
    {
	StringBuilder b = new StringBuilder (depth);
	for (int i = 0; i < depth; i++)
	    {
		b.append ("  ");
	    }
	return b.toString ();
    }
    
    private static void quoteData (String data, String prefix, BufferedWriter out)
	throws java.io.IOException
    {
	out.write (prefix + "{" + "\n");
	while (data.length () > 0)
	    {
		out.write (prefix + "  ;");
		int next = data.indexOf ("\n");
		if (next >= 0)
		    {
			out.write (data.substring (0, next));
			data = data.substring (next + 1);
			out.write ("\n");
			if (data.length () == 0)
			    {
				out.write (prefix + "  ;");
			    }
		    }
		else
		    {
			out.write (data);
			break;
		    }
	    }
	out.write ("\n" + prefix + "}" + "\n");
    }

    public void writeTo (BufferedWriter writer, String prefix)
	throws java.io.IOException
    {
	writer.write (prefix);
	if (this.name != null)
	    {
		writer.write (this.name + " ");
	    }
	writer.write ("{\n");
	if (this.ourData != null)
	    {
		for (String s : this.ourData)
		    {
			StdStructuredData.quoteData (s, prefix + "  ", writer);
		    }
	    }
	else if (this.children != null)
	    {
		for (StdStructuredData child : this.children.values ())
		    {
			child.writeTo (writer, prefix + "  ");
		    }
	    }
	writer.write (prefix + "}\n");
    }
    
    public void writeTo (BufferedWriter writer)
	throws java.io.IOException
    {
	this.writeTo (writer, "");
    }

    private class Ungettable
    {
	int prevCh = -1;
	BufferedReader str;

	Ungettable (BufferedReader strArg)
	{
	    this.str = strArg;
	}
	
	public int read ()
	    throws java.io.IOException
	{
	    if (this.prevCh >= 0)
		{
		    int i = this.prevCh;
		    this.prevCh = -1;
		    return i;
		}
	    return this.str.read ();
	}

	void unget (char ch)
	{
	    this.prevCh = ch;
	}
    }

    private void readData (Ungettable in, boolean isLeftCurly)
	throws java.io.IOException
    {
	int ich;
	StringBuffer res = new StringBuffer ();
	boolean needsNL = false;
	int state = (isLeftCurly ? 0 : 1);
	while ((ich = in.read ()) >= 0)
	    {
		char ch = (char) ich;
		if (state == 0)
		    {
			if (StdStructuredData.isName (ch))
			    {
				in.unget (ch);
				this.addData (res.toString ());
				return;
			    }
			else if (ch == ';')
			    {
				if (needsNL)
				    {
					res.append ('\n');
					needsNL = false;
				    }
				state = 1;
			    }
			else if (ch == '}')
			    {
				if (! isLeftCurly)
				    {
					in.unget (ch);
				    }
				this.addData (res.toString ());
				return;
			    }
		    }
		else if (state == 1)
		    {
			if (ch == '\n')
			    {
				state = 0;
				needsNL = true;
			    }
			else 
			    {
				res.append (ch);
			    }
		    }
	    }
    }

    private static boolean isName (char ch)
    {
	return Character.isLetterOrDigit (ch) || ch == '_';
    }
    
    private static String readName (Ungettable in)
	throws java.io.IOException
    {
	StringBuffer res = new StringBuffer ();
	int ich;
	while ((ich = in.read ()) >= 0)
	    {
		char ch = (char) ich;
		if (StdStructuredData.isName (ch))
		    {
			res.append (ch);
		    }
		else
		    {
			in.unget (ch);
			break;
		    }
	    }
	return res.toString ();
    }

    public void readFrom (BufferedReader readerIn)
	throws java.io.IOException
    {
	Ungettable in = new Ungettable (readerIn);
	this.readFrom (in);
    }

    private void readFrom (Ungettable in)
	throws java.io.IOException
    {
	int ich;
	int state = 0;

	while ((ich = in.read ()) >= 0)
	    {
		char ch = (char) ich;
		if (state == 0)
		    {
			if (StdStructuredData.isName (ch))
			    {
				in.unget (ch);
				this.name = StdStructuredData.readName (in);
				state = 1;
			    }
			else if (ch == '{')
			    {
				state = 2;
			    }
			else if (ch == ';')
			    {
				this.readData (in, false);
				return;
			    }
		    }
		else if (state == 1)
		    {
			if (ch == '{')
			    {
				state = 2;
			    }
			else if (ch == ';')
			    {
				in.unget (ch);
				state = 2;
			    }
		    }
		else if (state == 2)
		    {
			if (ch == '}')
			    {
				return;
			    }
			else if (StdStructuredData.isName (ch))
			    {
				in.unget (ch);
				String subName = StdStructuredData.readName (in);
				StdStructuredData d
				    = this.doAddOrReturnChild (subName);
				d.readFrom (in);
			    }
			else if (ch == '{')
			    {
				this.readData (in, true);
			    }
			else if (ch == ';')
			    {
				this.readData (in, false);
			    }
		    }
	    }
    }
}
