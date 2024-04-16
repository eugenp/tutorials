package com.baeldung.imageprocessing.imagetobufferedimage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
            throw new ClassCastException("Image type is not compatible with BufferedImage");
        }
    }

    // Method 3: Using ImageIO Class
    public BufferedImage convertUsingImageIO(String filePath) throws IOException {
        try {
            File file = new File(filePath);
            return ImageIO.read(file);
        } catch (Exception e) {
            throw new IOException("Error reading image file: " + e.getMessage());
        }
    }
}
