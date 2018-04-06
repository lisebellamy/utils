package com.lisebellamy.util;

import java.io.BufferedReader;
import java.io.FileReader;

public class MyControlFileReader
{
    private BufferedReader reader;
    private String putback = null;

    public MyControlFileReader (FileReader fileArg)
    {
	this.reader = new BufferedReader (fileArg);
    }

    public void putbackLine (String arg)
    {
	this.putback = arg;
    }

    public String getNBLine (String prefix)
	throws java.io.IOException
    {
	while (true)
	    {
		String line = this.getLine ();
		if (line != null)
		    {
			line = line.trim ();
			if (line.length () == 0)
			    {
				continue;
			    }
			if (prefix.length () > 0)
			    {
				if ((line.length () < prefix.length ())
				    || (line.substring (0, prefix.length ()).compareTo (prefix) != 0))
				    {
					throw new java.io.IOException ("Missing entry for " + prefix + ", " + line);
				    }
			    }
			return line.substring (prefix.length ()).trim ();
		    }
		else
		    {
			return line;
		    }
	    }
    }

    String getLine ()
	throws java.io.IOException
    {
	String res;
	if (this.putback != null)
	    {
		res = this.putback;
		this.putback = null;
		return res;
	    }
	while (true)
	    {
		res = this.reader.readLine ();
		if (res == null)
		    {
			return res;
		    }
		int i = res.indexOf ("//");
		    {
			if (i < 0)
			    {
				return res;
			    }
			else if (i > 0)
			    {
				return res.substring (0, i);
			    }
		    }
	    }
    }
}
