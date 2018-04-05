// This is -*-java-*-
package com.lisebellamy.util;
%{
#define SparseArray([trim]objType)

#define nil #ifelse(#streq(#objType#,int)# || #streq(#objType#,double)# || #streq(#objType#,char)# || #streq(#objType#,float)# || #streq(#objType#,byte)#,0,null)#

public class SparseArray
{
    private boolean invalid = true;
    
    private interface BinTree
    {
	#objType# get (int index);
    }
    
    private class SimpleEnt
	implements BinTree
    {
	private int ourIndex;
	private #objType# obj;

	SimpleEnt (int ourIndexArg, #objType# objArg)
	{
	    this.ourIndex = ourIndexArg;
	    this.obj = objArg;
	}
	
	#objType# get (int index)
	{
	    if (index == this.ourIndex)
		{
		    return this.obj;
		}
	    else
		{
		    return #nil#;
		}
	}
    }

    private class SimpleArrayEnt
	implements BinTree
    {
	private final #objType#[] array;

	SimpleArrayEnt (int size)
	{
	    this.array = new #objType#[size];
	}

	#objType# get (int index)
	{
	    if (index < 0 || index >= this.array.length)
		{
		    return #nil#;
		}
	    else
		{
		    return this.array[index];
		}
	}
    }

    private class NullTreeEnt
	implements BinTree
    {
	#objType# get (int index)
	{
	    return #nil#;
	}
    }
    
    private class SimpleTreeEnt
	implements BinTree
    {
	private int pivot;
	private #objType# obj;
	private BinTree left;
	private BinTree right;

	SimpleTreeEnt (int pivotArg, #objType# objArg)
	{
	    this.pivot = pivotArg;
	    this.obj = objArg;
	}

	void setLeft (BinTree leftArg)
	{
	    this.left = leftArg;
	}

	void setRight (BinTree rightArg)
	{
	    this.right = rightArg;
	}

	#objType# get (int index)
	{
	    index -= this.pivot;
	    if (index == 0)
		{
		    return this.obj;
		}
	    else if (index < 0)
		{
		    return this.left.get (index);
		}
	    else
		{
		    return this.right.get (index);
		}
	}   
    }
    
    private int minIndex;
    private int maxIndex = -1;
    private final int maxArraySize;
    private AVLTreeInt<#objType> tree;
    
    public SparseArray (int maxArraySizeArg)
    {
	this.maxArraySize = maxArraySizeArg;
    }

    void add (int index, #objType# obj)
    {
	if (index > this.maxIndex)
	    {
		if (this.maxIndex == -1)
		    {
			this.minIndex = index;
		    }
		this.maxIndex = index;
	    }
	if (index < this.minIndex)
	    {
		this.minIndex = index;
	    }
	this.tree.add (index, obj);
	this.invalid = true;
    }

    void optimize ()
    {
	if (! this.invalid)
	    {
		return;
	    }
    }
}
}%
