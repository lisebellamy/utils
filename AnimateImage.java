package com.lisebellamy.util;

import java.awt.image.BufferedImage;

import javax.swing.event.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Graphics;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.Date;
import java.awt.Color;
import java.awt.*;

public class AnimateImage
    implements Runnable
{
    private BufferedImage animImage;
    private Graphics2D gForAnimImage;
    private BufferedImage animSrcImage;
    private Graphics2D gForAnimSrcImage;
    private BufferedImage subImageFrom;
    private BufferedImage subImageTo;
    private Timer timer;
    private long startTime;
    private long animTime;
    private boolean goUp;
    private TheTimer theT;
    private int width;
    private int height;

    private class TheTimer
	extends TimerTask
    {
	private Runnable r;
	TheTimer (Runnable rArg)
	{
	    r = rArg;
	}

	public void run ()
	{
	    this.r.run ();
	}
    }

    public static interface AnimUpdate
    {
	void setAnimImage (ScaledImage img);
    }

    private AnimUpdate dest = null;
    
    public AnimateImage (AnimUpdate destArg,
			 BufferedImage subImageFromArg,
			 BufferedImage subImageToArg,
			 long timeToTransitionMS,
			 boolean goUpArg)
    {
	this.width = subImageFromArg.getWidth ();
	this.height = subImageFromArg.getHeight ();
	this.goUp = goUpArg;
	this.animImage = new BufferedImage (width, height,
					    BufferedImage.TYPE_INT_ARGB);
	
	this.gForAnimImage = this.animImage.createGraphics ();
	this.gForAnimImage.setComposite (AlphaComposite.getInstance (AlphaComposite.SRC));
	this.dest = destArg;
	this.animTime = timeToTransitionMS;
	this.subImageFrom = subImageFromArg;
	this.subImageTo = subImageToArg;
	this.animSrcImage = new BufferedImage (this.width, this.height * 2,
					       BufferedImage.TYPE_INT_ARGB);
	this.gForAnimSrcImage = this.animSrcImage.createGraphics ();
	this.gForAnimSrcImage.setComposite (AlphaComposite.getInstance (AlphaComposite.SRC));
	Graphics2D g = this.gForAnimSrcImage;
	this.gForAnimImage.setComposite (AlphaComposite.getInstance (AlphaComposite.SRC));
	if (! this.goUp)
	    {
		g.drawImage (this.subImageTo,
			     0, 0, this.width, this.height, null);
		g.drawImage (this.subImageFrom,
			     0, this.height, this.width, this.height,
			     null);
	    }
	else
	    {
		g.drawImage (this.subImageFrom,
			     0, 0, width, height,
			     null);
		g.drawImage (this.subImageTo,
			     0, height, width, height,
			     null);
	    }
	this.startTime = System.currentTimeMillis ();
	if (this.timer == null)
	    {
		this.timer = new Timer ();
		this.theT = new TheTimer (this);
		this.timer.schedule (this.theT,
				     0, (long) (1000 / 30.0 + 0.5));
	    }
    }

    public void run ()
    {
	this.drawFromTo ();
    }

    private void drawFromTo ()
    {
	double ratio = System.currentTimeMillis () - this.startTime;
	ratio = ratio / (double) this.animTime;
	if (ratio >= 1.0)
	    {
		if (this.timer != null)
		    {
			this.timer.cancel ();
			this.timer.purge ();
			this.theT.cancel ();
			this.timer = null;
		    }
		this.dest.setAnimImage (new StdScaledImage (this.subImageTo));
		return;
	    }
	Graphics2D g = this.gForAnimImage;

	int startHeight = (int) (this.height * ratio + 0.5);

	if (! this.goUp)
	    {
		startHeight = this.height - startHeight;
	    }

	BufferedImage s
	    = this.animSrcImage.getSubimage (0, startHeight,
					     this.width, this.height);
	g.drawImage (s,
		     0, 0, this.width, this.height,
		     null);
	this.dest.setAnimImage (new StdScaledImage (this.animImage));
    }
}
