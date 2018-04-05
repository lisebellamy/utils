package com.lisebellamy.util;
import java.nio.file.Path;

public interface ImageCache
{
    public boolean addImage (String name, Path file)
	throws java.io.IOException;
    public void addImage (String name, ScaledImage image, ImageCoords coords);
    public ScaledImage readImage (String name, Path filename)
	throws java.io.IOException;
    ScaledImage getScaledImage (String name);
    ImageCoords getCoords (String name);
}
