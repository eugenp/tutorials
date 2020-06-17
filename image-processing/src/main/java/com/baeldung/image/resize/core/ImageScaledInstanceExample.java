package com.baeldung.image.resize.core;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageScaledInstanceExample {
    static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage bufferedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics()
            .drawImage(resultingImage, 0, 0, null);
        return bufferedImage;
    }

    public static void main(String[] args) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File("src/main/resources/images/sampleImage.jpg"));
        BufferedImage outputImage = resizeImage(originalImage, 200, 200);
        ImageIO.write(outputImage, "jpg", new File("src/main/resources/images/sampleImage-resized-scaledinstance.jpg"));
    }
}
