package com.lisebellamy.util;
import java.util.Iterator;
import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
	
public class DLL<T>
    implements Iterable<T>
{
    private final DLLObjEnt beginList;
    private final DLLObjEnt endList;
    private static int listNums = 0;
    private final int ourListNum = DLL.listNums++;
    private int elementCount = 0;
    private int ourID = 0;

    public DLLObj genHandle (T obj)
    {
	return new DLLObj (obj);
    }

    public DLLObj genHandle ()
    {
	return new DLLObj ((T) null);
    }
    
    public class DLLObj
    {
	private final DLLObjEnt objEnt;
	private T object;

	private DLLObj (T o)
	{
	    this.objEnt = new DLLObjEnt (this);
	    this.object = o;
	}

	private DLL<T> getList ()
	{
	    return DLL.this;
	}

	public void setObj (T o)
	{
	    this.object = o;
	}

	public T getObj ()
	{
	    return this.object;
	}

	private boolean insertBefore (DLLObjEnt before)
	{
	    return this.objEnt.insertBefore (before);
	}
	
	public boolean insertBefore (DLLObj before)
	{
	    assert (before.getList () == DLL.this);
	    return this.insertBefore (before.objEnt);
	}

	public boolean add ()
	{
	    return this.insertBefore (DLL.this.endList);
	}

	public boolean isContainedBy ()
	{
	    return this.objEnt.isContained ();
	}

	public boolean remove ()
	{
	    return this.objEnt.remove ();
	}

	public DLLObj next ()
	{
	    DLLObjEnt ent = this.objEnt.next ();
	    if (ent != null)
		{
		    return ent.dllObj;
		}
	    return null;
	}

	public boolean hasNext ()
	{
	    return this.objEnt.hasNext ();
	}

	public boolean hasPrev ()
	{
	    return this.objEnt.hasPrev ();
	}

	public DLLObj prev ()
	{
	    DLLObjEnt ent = this.objEnt.prev ();
	    if (ent != null)
		{
		    return ent.dllObj;
		}
	    return null;
	}
	
    }

    private class DLLObjEnt
    {
	private final DLLObj dllObj;
	private DLLObjEnt prev;
	private DLLObjEnt next;
	private int ourID;

	private DLLObjEnt (DLLObj objArg)
	{
	    this.dllObj = objArg;
	    this.ourID = -1;
	}

	private DLLObjEnt (DLL<T> listArg)
	{
	    listArg.beginList.next = this;
	    this.ourID = listArg.ourID;
	    listArg.beginList.ourID = this.ourID;
	    this.prev = listArg.beginList;
	    this.dllObj = null;
	}

	public boolean hasNext ()
	{
	    return (DLL.this.ourID == this.ourID)
		&& (this.next != DLL.this.endList);
	}

	final public T getObj ()
	{
	    return this.dllObj.object;
	}

	public final DLLObjEnt next ()
	{
	    if (DLL.this.ourID == this.ourID)
		{
		    DLLObjEnt n = this.next;
		    if (n != DLL.this.endList)
			{
			    return n;
			}
		}
	    return null;
	}

	public final boolean hasPrev ()
	{
	    return (DLL.this.ourID == this.ourID)
		&& this.prev != DLL.this.beginList;
	}

	public final DLLObjEnt prev ()
	{
	    if (DLL.this.ourID == this.ourID)
		{
		    DLLObjEnt p = this.prev;
		    if (p != DLL.this.beginList)
			{
			    return p;
			}
		}
	    return null;
	}

	public final boolean insertBefore (DLLObjEnt before)
	{
	    if (DLL.this.ourID == this.ourID)
		{
		    return false;
		}

	    this.ourID = DLL.this.ourID;
	    this.prev = before.prev;
	    this.next = before;
	    before.prev.next = this;
	    before.prev = this;
	    return true;
	}

	public final boolean remove ()
	{
	    if (this.ourID != DLL.this.ourID)
		{
		    return false;
		}
	    DLLObjEnt p = this.prev;
	    DLLObjEnt n = this.next;
	    p.next = n;
	    n.prev = p;
	    this.ourID = -1;
	    return true;
	}

	public boolean isContained ()
	{
	    return (this.ourID == DLL.this.ourID);
	}
    }

    private DLLObjEnt firstDLLObj ()
    {
	return this.beginList.next ();
    }

    public DLLObj first ()
    {
	DLLObjEnt e = this.beginList.next ();
	if (e != null)
	    {
		return e.dllObj;
	    }
	return null;
    }

    public DLLObj last ()
    {
	DLLObjEnt e = this.endList.prev ();
	if (e != null)
	    {
		return e.dllObj;
	    }
	return null;
    }

    private final class DLLIterator
	implements Iterator<T>
    {
	private DLLObjEnt cursor;

	public DLLIterator (DLL<T> list)
	{
	    this.cursor = DLL.this.beginList;
	}

	final public boolean hasNext ()
	{
	    return this.cursor.hasNext ();
	}

	final public T next ()
	{
	    DLLObjEnt nextEnt = this.cursor.next ();
	    this.cursor = nextEnt;
	    return nextEnt.getObj ();
	}

	public void remove ()
	{
	    DLLObjEnt obj = this.cursor;
	    this.cursor = this.cursor.next ();
	    DLL.this.remove (obj);
	}
    }

    public Iterator<T> iterator () { return new DLLIterator (this); }
    
    public DLL ()
    {
	this.beginList = new DLLObjEnt ((DLLObj) null);
	this.endList = new DLLObjEnt (this);
    }

    public boolean add (DLLObj obj)
    {
	if (obj.insertBefore (this.endList))
	    {
		this.elementCount++;
		return true;
	    }
	else
	    {
		return false;
	    }
    }

    public boolean contains (DLLObj obj)
    {
	return obj.isContainedBy ();
    }

    public boolean isEmpty ()
    {
	if (this.elementCount == 0)
	    {
		assert this.beginList.next == this.endList;
		assert this.beginList == this.endList.prev;
		return true;
	    }
	else
	    {
		return false;
	    }
    }

    private boolean remove (DLLObjEnt ent)
    {
	if (ent.remove ())
	    {
		this.elementCount--;
		return true;
	    }
	else
	    {
		return false;
	    }
    }

    public boolean remove (DLLObj obj)
    {
	if (obj.remove ())
	    {
		this.elementCount--;
		return true;
	    }
	else
	    {
		return false;
	    }
    }

    public void clear ()
    {
	this.elementCount = 0;
	this.ourID++;
	this.beginList.ourID = this.ourID;
	this.endList.ourID = this.ourID;
	this.beginList.next = this.endList;
	this.endList.prev = this.beginList;
    }

    public int size ()
    {
	return this.elementCount;
    }

    public Object[] toArray ()
    {
	Object[] res = new Object[this.elementCount];
	DLLObjEnt cursor = this.beginList.next ();
	int i = 0;
	while (cursor != null)
	    {
		res[i] = cursor.getObj ();
		i++;
		cursor = cursor.next ();
	    }
	assert i == this.elementCount;
	return res;
    }

    public T[] toArray (T[] res)
    {
	assert res != null;
	// This is absurd.
	Class<?> ct = res.getClass ().getComponentType ();
	if (res.length < this.elementCount)
	    {
		@SuppressWarnings("unchecked")
		    T[] res2 = (T[]) Array.newInstance (ct, this.elementCount);
		res = res2;
	    }
	DLLObjEnt cursor = this.beginList.next ();
	int i = 0;
	while (cursor != null)
	    {
		res[i] = cursor.getObj ();
		i++;
		cursor = cursor.next ();
	    }
	assert i == this.elementCount;
	if (i < res.length)
	    {
		res[i] = null;
	    }
	return res;
    }

    public ArrayList<DLLObj> toDLLObjArray ()
    {
	ArrayList<DLLObj> res
	    = new ArrayList<DLLObj> (this.elementCount);
	DLLObjEnt cursor = this.beginList.next ();
	while (cursor != null && cursor != this.endList)
	    {
		res.add (cursor.dllObj);
		cursor = cursor.next ();
	    }
	assert res.size () == this.elementCount;
	return res;
    }
}
