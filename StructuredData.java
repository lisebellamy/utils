package com.lisebellamy.util;
import java.util.List;
import java.util.Set;
import java.io.BufferedWriter;
import java.io.BufferedReader;

/**
 * An interface for handling structured String data. Strings may
 * contain any characters; the implementation is required to quote and
 * dequote as necessary.
 */
public interface StructuredData
{
    /**
     * Returns the name of this node.
     *
     * @return a String representing the name of this node.
     */
    public String getName ();

    /**
     * Indicates if this is a data node.
     *
     * @return true if node is a data node
     */
    public boolean isDataNode ();

    /**
     * Indicates if this node has multiple data sets.
     *
     * @return true if more than one set of data exists in this node
     */
    public boolean hasMultipleData ();

    /**
     * Retrieve the data from this node.
     *
     * @return a list of data stored in this node
     *
     * @throws IllegalArgumentException if this node has children
     */
    public List<String> getData ();

    /**
     * Retrieve the data from the given child node.
     *
     * @return a single datum stored in the child node, or null if
     *         child does not exist or has no data
     *
     * @throws IllegalArgumentException if the child node has children
     */
    public String getData (String childName);
    
    /**
     * Sets the data in this node.
     *
     * @param data data to assign to this node
     *
     * @throws IllegalArgumentException if this node contains multiple sets of
     *         data or has children
     */
    public void setData (String data);

    /**
     * Sets the data in the named child, typically via invoking
     * setData() on the node. If the node does not exist, it is
     * created.
     *
     * @param childName the child node name
     * @param data the data to set
     *
     * @throws IllegalArgumentException if the named child node
     *         contains multiple sets of data or has children
     */
    public void setData (String childName, String data);
    
    /**
     * Adds a new data node.
     *
     * @param data the data to be added to this node
     *
     * @throws IllegalArgumentException if node contains children
     */
    public void addData (String data);

    /**
     * Adds data to the named child, typically via invoking addData()
     * on the node. If the node does not exist, it is created.
     *
     * @param childName the child node name
     * @param data the data to set
     *
     * @throws IllegalArgumentException if the named child node has
     *         children
     */
    public void addData (String childName, String data);
    
    /**
     * Returns the set of child nodes.
     *
     * @return set of child nodes, or null if no children
     */
    public Set<String> getChildNames ();

    /**
     * Returns the child with given name.
     *
     * @param childName the name of the child
     *
     * @return the specified child node, or null if child does not exist
     */
    public StructuredData getChild (String childName);
    
    /**
     * Add a child node.
     *
     * @param childName the child node to be added
     *
     * @return the existing child node, or a new one
     * 
     * @throws IllegalArgumentException if this is a data node
     */
    public StructuredData addOrReturnChild (String childName);

    /**
     * Writes a representation of this node and its children such
     * that it may be read via readFrom().
     * 
     * @param writer the writer to write to
     *
     * @throws IOException on I/O errors
     */
    public void writeTo (BufferedWriter writer)
	throws java.io.IOException;

    /**
     * Reads a representation of this node and its children.
     *
     * @param reader the reader to read from
     *
     * @throws IOException on I/O errors or malformed input stream
     */
    public void readFrom (BufferedReader reader)
	throws java.io.IOException;
}
 
