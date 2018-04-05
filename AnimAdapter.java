package com.lisebellamy.util;

import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import javax.accessibility.*;
import java.util.Map;
import java.util.HashMap;

import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.sound.midi.MidiMessage;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.text.*;
import java.beans.*;

public class AnimAdapter
    implements AnimateImage.AnimUpdate
{
    private ImagePanel panel;
    private ImageList.Position pos;
    private AnimateImage animator;
    private int destImage;

    public AnimAdapter (ImagePanel panelArg, ImageList.Position posArg,
			int destImageArg,
			BufferedImage from, BufferedImage to, long delayInMS,
			boolean goUp)
    {
	this.panel = panelArg;
	this.pos = posArg;
	this.destImage = destImageArg;
	this.animator = new AnimateImage (this,
					  from,
					  to,
					  delayInMS,
					  goUp);
    }

    public void setAnimImage (ScaledImage img)
    {
	this.panel.setImage (this.destImage, img, this.pos);
    }
}
