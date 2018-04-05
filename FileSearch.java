package com.lisebellamy.util;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class FileSearch
{
    final private static List<Path> imgDirs = new ArrayList<Path> ();

    public void addDir (String path)
    {
	Path p = Paths.get (path);
	this.imgDirs.add (p);
    }

    public void addDir (Path p)
    {
	this.imgDirs.add (p);
    }

    public Path search (Path name)
    {
	for (int i = 0; i < this.imgDirs.size (); i++)
	    {
		Path n = this.imgDirs.get (i).resolve (name);
		if (Files.exists (n))
		    {
			return n;
		    }
	    }
	return null;
    }
}
 
