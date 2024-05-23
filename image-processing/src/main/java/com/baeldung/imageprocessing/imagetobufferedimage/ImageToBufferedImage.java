package com.baeldung.imageprocessing.imagetobufferedimage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageToBufferedImage {

    // Method 1: Using BufferedImage Constructor
    public BufferedImage convertUsingConstructor(Image image) throws IllegalArgumentException {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Image dimensions are invalid");
        }
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(image, 0, 0, null);
        return bufferedImage;
    }

    // Method 2: Casting Image to BufferedImage
    public BufferedImage convertUsingCasting(Image image) throws ClassCastException {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        } else {
            throw new ClassCastException("Image type is not compatible with BufferedImage.");
        }
    }
}
