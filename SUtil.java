package com.lisebellamy.util;

public class SUtil
{
    public static String matchPrefix (String prefix, String in)
    {
	if (in.length () >= prefix.length ())
	    {
		if (in.substring (0, prefix.length ()).equalsIgnoreCase (prefix))
		    {
			return in.substring (prefix.length ());
		    }
	    }
	return null;
    }

    public static boolean begins (String in, String prefix)
    {
	if (in.length () >= prefix.length ())
	    {
		return in.substring (0, prefix.length ()).equalsIgnoreCase (prefix);
	    }
	return false;
    }
}
