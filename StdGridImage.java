package com.lisebellamy.util;

import java.awt.image.BufferedImage;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.Graphics;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.Date;
import java.awt.Color;
import java.awt.*;

public class StdGridImage
    implements GridImage
{
    private BufferedImage image;
    private int numColumns;
    private int initialXOffset;
    private int spacingBetweenColumns;
    private int columnSize;
    private int initialYOffset;
    private int spacingBetweenRows;
    private int rowSize;
    private int numImages;

    public StdGridImage (BufferedImage imageArg,
			 int numColumnsArg,
			 int initialXOffsetArg, int spacingBetweenColumnsArg,
			 int columnSizeArg,
			 int initialYOffsetArg, int spacingBetweenRowsArg,
			 int rowSizeArg)
    {
	this.image = imageArg;
	this.numColumns = numColumnsArg;
	this.initialXOffset = initialXOffsetArg;
	this.spacingBetweenColumns = spacingBetweenColumnsArg;
	this.columnSize = columnSizeArg;
	this.initialYOffset = initialYOffsetArg;
	this.spacingBetweenRows = spacingBetweenRowsArg;
	this.rowSize = rowSizeArg;
    }

    public StdGridImage (BufferedImage imageArg,
			 int numRowsArg,
			 int numColumnsArg)
    {
	this (imageArg, numColumnsArg,
	      0, imageArg.getWidth () / numColumnsArg,
	      imageArg.getWidth () / numColumnsArg,
	      0, imageArg.getHeight () / numRowsArg,
	      imageArg.getHeight () / numRowsArg);
    }

    public int getWidth ()
    {
	return this.columnSize;
    }

    public int getHeight ()
    {
	return this.rowSize;
    }

    private Integer[] getRectForPosition (int pos)
    {
	Integer[] res = new Integer[4];
	int column = pos % this.numColumns;
	int row = pos / this.numColumns;
	res[0] = this.initialXOffset + column * this.spacingBetweenColumns;
	res[1] = this.initialYOffset + row * this.spacingBetweenRows;
	res[2] = this.columnSize;
	res[3] = this.rowSize;
	return res;
    }
       
    public BufferedImage getImageForPosition (int pos)
    {
	Integer[] start = getRectForPosition (pos);
	return this.image.getSubimage (start[0],
				       start[1],
				       start[2],
				       start[3]);
    }

    public int getNumberOfImages ()
    {
	int numPerRow = (this.image.getWidth () - this.initialXOffset) / this.spacingBetweenColumns;
	int numPerCol = (this.image.getHeight () - this.initialYOffset) / this.spacingBetweenRows;
	return numPerRow * numPerCol;
    }
}
