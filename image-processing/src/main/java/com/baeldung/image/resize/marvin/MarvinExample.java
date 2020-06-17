package com.baeldung.image.resize.marvin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.marvinproject.image.transform.scale.Scale;

import marvin.image.MarvinImage;

public class MarvinExample {
    static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        MarvinImage image = new MarvinImage(originalImage);
        Scale scale = new Scale();
        scale.load();
        scale.setAttribute("newWidth", targetWidth);
        scale.setAttribute("newHeight", targetHeight);
        scale.process(image.clone(), image, null, null, false);
        return image.getBufferedImage();
    }

    public static void main(String args[]) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File("src/main/resources/images/sampleImage.jpg"));
        BufferedImage outputImage = resizeImage(originalImage, 200, 200);
        ImageIO.write(outputImage, "jpg", new File("src/main/resources/images/sampleImage-resized-marvin.jpg"));
    }
}
