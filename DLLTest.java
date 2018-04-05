package com.lisebellamy.util;
import java.util.ArrayList;

public class DLLTest
{
    static private DLL<Integer> list = new DLL<Integer> ();
    static int values[] = new int[100];
    static ArrayList<DLL<Integer>.DLLObj> objList = new ArrayList<DLL<Integer>.DLLObj> ();

    public static void main (String[] args)
    {
	int count = 0;
	for (int i = 0; i < args.length; i += 2)
	    {
		int val = Integer.parseInt (args[i + 1]);
		if (args[i].equals ("add"))
		    {
			DLLTest.values[count] = val;
			DLL<Integer>.DLLObj obj = DLLTest.list.genHandle ((Integer) val);
			DLLTest.objList.add (obj);
			DLLTest.list.add (obj);
			count++;
		    }
		else if (args[i].equals ("remove"))
		    {
			DLLTest.list.remove ((DLL<Integer>.DLLObj) DLLTest.objList.get (val));
			DLLTest.objList.remove (val);
			for (int j = val; j < count; j++)
			    {
				DLLTest.values[j] = DLLTest.values[j + 1];
			    }
			count--;
		    }
	    }
	assert DLLTest.list.size () == count;
	System.out.println ("size = " + DLLTest.list.size ());
	int pos = 0;
	for (Integer i : DLLTest.list)
	    {
		System.out.println (i + ", " + DLLTest.values[pos]);
		pos++;
	    }
	Integer foo[] = new Integer[1];
	foo = DLLTest.list.toArray (foo);
	for (Integer i : foo)
	    {
		System.out.println (i);
	    }
	for (int i = 0; i < count; i++)
	    {
		DLL<Integer>.DLLObj obj = DLLTest.objList.get (i);
		assert DLLTest.list.contains (obj);
	    }
	DLLTest.list.clear ();
	assert DLLTest.list.size () == 0;
	for (int i = 0; i < count; i++)
	    {
		DLL<Integer>.DLLObj obj = DLLTest.objList.get (i);
		assert ! DLLTest.list.contains (obj);
		DLLTest.list.add (obj);
	    }
	assert DLLTest.list.size () == count;
	for (int i = 0; i < count; i++)
	    {
		DLL<Integer>.DLLObj obj = DLLTest.objList.get (i);
		assert DLLTest.list.contains (obj);
		System.out.println (obj.getObj ());
	    }
	ArrayList<DLL<Integer>.DLLObj> lt = DLLTest.list.toDLLObjArray ();
	assert lt.size () == count;
	for (int i = 0; i < count; i++)
	    {
		assert lt.get (i) == DLLTest.objList.get (i);
	    }
    }
}
