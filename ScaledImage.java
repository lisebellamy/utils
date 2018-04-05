package com.lisebellamy.util;

import java.awt.image.BufferedImage;
import java.awt.Point;

public interface ScaledImage
{
    public BufferedImage getImage (int width, int height);
    public BufferedImage getRawImage ();
    public int getRawWidth ();
    public int getRawHeight ();
    // WidthxHeight; if the raw image is 2x as wide as it is high, then the
    // result is 2.
    public double getAspectRatio ();
}
