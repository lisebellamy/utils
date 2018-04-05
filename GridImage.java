package com.lisebellamy.util;

import java.awt.geom.*;
import java.awt.image.*;

public interface GridImage
{
    int getNumberOfImages ();
    int getWidth ();
    int getHeight ();
    BufferedImage getImageForPosition (int whichImage);
}
