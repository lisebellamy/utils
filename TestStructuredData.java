package com.lisebellamy.util;
import java.io.BufferedWriter;
import java.io.StringWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;

public class TestStructuredData
{
    public static void main (String[] args)
    {
	if (args.length == 0)
	    {
		StructuredData d = new StdStructuredData ("top");
		StructuredData kid = d.addOrReturnChild ("kid");
		kid.setData ("hey\nthere");
		kid.addData ("{\nlet's go!\n}");
		StructuredData k2 = d.addOrReturnChild ("another");
		StructuredData k3 = k2.addOrReturnChild ("another");
		k3.addData ("boring");
		StringWriter s = new StringWriter ();
		BufferedWriter bs = new BufferedWriter (s);
		try {
		    d.writeTo (bs);
		    bs.flush ();
		} catch (Exception e)
		    {
			e.printStackTrace ();
		    }
		System.out.println (s.toString ());
	    }
	else
	    {
		StringWriter s = new StringWriter ();
		try {
		    FileReader fr = new FileReader (args[0]);
		    BufferedReader br = new BufferedReader (fr);
		    StructuredData d = new StdStructuredData ();
		    d.readFrom (br);
		    BufferedWriter bs = new BufferedWriter (s);
		    d.writeTo (bs);
		    bs.flush ();
		    StructuredData kid = d.getChild ("kid");
		    List<String> data = kid.getData ();
		    assert data.get (0).equals ("hey\nthere");
		    assert data.get (1).equals ("{\nlet's go!\n}");
		    StructuredData k2 = d.getChild ("another");
		    StructuredData k3 = k2.getChild ("another");
		    data = k3.getData ();
		    assert data.get (0).equals ("boring");
		} catch (Exception e)
		    {
			e.printStackTrace ();
		    }
		System.out.println (s.toString ());
	    }
    }
}

