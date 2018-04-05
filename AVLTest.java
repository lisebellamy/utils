package com.lisebellamy.util;
import java.util.HashSet;

public class AVLTest
{
    public static void main (String[] args)
    {
	AVLTreeInt<Integer> tree = new AVLTreeInt<Integer> ();
	HashSet<Integer> set = new HashSet<Integer> ();
	Integer[] inSet = new Integer[1024];
	Integer[] keys = new Integer[1024];

	for (int x = 0; x < 1024; x++)
	    {
		int r = (int) (Math.random () * 1024);
		inSet[x] = r;
		keys[x] = x;
	    }
	for (int x = 0; x < 10000; x++)
	    {
		int r = (int) (Math.random () * 2048);
		int key = r & 1023;
	    
		if (r > 1023)
		    {
			tree.insert (key, inSet[key]);
			set.add (keys[key]);
			assert tree.retrieve (key) != null;
			for (Integer i : set)
			    {
				if (tree.retrieve ((int) i) == null)
				    {
					assert false;
				    }
			    }
		    }
		else
		    {
			if (set.contains (keys[key]))
			    {
				assert tree.retrieve (key) != null;
				tree.remove (key);
				set.remove (keys[key]);
				assert tree.retrieve (key) == null;
				for (Integer i : set)
				    {
					if (tree.retrieve ((int) i) == null)
					    {
						assert false;
					    }
				    }
			    }
		    }
		tree.validateTree ();
	    }
	for (Integer key : set)
	    {
		Integer r = tree.retrieve ((int) key);
		if (r == null)
		    {
			System.out.println (key);
			assert r != null;
		    }
		assert r == inSet[(int) key];
	    }
    }
}
