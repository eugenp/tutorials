package com.baeldung.triton.yolo;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImagePreprocessor {

    public static float[] preprocessFromResources(String resourcePath, int targetWidth, int targetHeight) throws IOException {
        // 1. Load image from classpath (src/main/resources)
        InputStream is = ImagePreprocessor.class.getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) {
            throw new IllegalArgumentException("Resource file not found in classpath: " + resourcePath);
        }
        
        BufferedImage originalImage = ImageIO.read(is);
        if (originalImage == null) {
            throw new IOException("Failed to decode image from: " + resourcePath);
        }

        // 2. Resize image to 640x640 RGB
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH), 0, 0, null);
        g.dispose();

        // 3. Convert to Planar (NCHW) format: [1, 3, targetHeight, targetWidth]
        int totalPixels = targetWidth * targetHeight;
        float[] tensorData = new float[3 * totalPixels];

        int rOffset = 0;
        int gOffset = totalPixels;
        int bOffset = 2 * totalPixels;

        for (int y = 0; y < targetHeight; y++) {
            for (int x = 0; x < targetWidth; x++) {
                int rgb = resizedImage.getRGB(x, y);
                
                int r = (rgb >> 16) & 0xFF;
                int gVal = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                int index = y * targetWidth + x;
                
                // Normalize to [0.0, 1.0] and store in planar channels
                tensorData[rOffset + index] = r / 255.0f;
                tensorData[gOffset + index] = gVal / 255.0f;
                tensorData[bOffset + index] = b / 255.0f;
            }
        }

        System.out.printf("Successfully preprocessed %s into tensor shape [1, 3, %d, %d]%n", 
                resourcePath, targetHeight, targetWidth);
        return tensorData;
    }
}