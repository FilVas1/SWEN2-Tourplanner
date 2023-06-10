package com.semesterproject.tourplanner.bl;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageHelper {
    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        //create a bufferedImage with transparency
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        //draw the image on the bufferedImage
        Graphics2D bufferedImageGraphics = bufferedImage.createGraphics();
        bufferedImageGraphics.drawImage(img, 0, 0, null);
        bufferedImageGraphics.dispose();

        //return a bufferedImage
        return bufferedImage;
    }
}
