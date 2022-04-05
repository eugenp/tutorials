package com.baeldung.image.resize.core;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Graphics2DExample {

    static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

    public static void main(String[] args) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File("src/main/resources/images/sampleImage.jpg"));
        BufferedImage outputImage = resizeImage(originalImage, 200, 200);
        ImageIO.write(outputImage, "jpg", new File("src/main/resources/images/sampleImage-resized-graphics2d.jpg"));
    }
}
