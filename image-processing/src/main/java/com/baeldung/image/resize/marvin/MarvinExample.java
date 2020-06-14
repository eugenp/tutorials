package com.baeldung.image.resize.marvin;

import org.marvinproject.image.transform.scale.Scale;

import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;

public class MarvinExample {
    static void resizeImage(String originalImagePath, int targetWidth, int targetHeight, String outputImagePath) {
        MarvinImage image = MarvinImageIO.loadImage(originalImagePath);
        Scale scale = new Scale();
        scale.load();
        scale.setAttribute("newWidth", targetWidth);
        scale.setAttribute("newHeight", targetHeight);
        scale.process(image.clone(), image, null, null, false);
        MarvinImageIO.saveImage(image, outputImagePath);
    }

    public static void main(String args[]) {
        resizeImage("src/main/resources/images/sampleImage.jpg", 200, 200, "src/main/resources/images/sampleImage1.jpg");
    }
}
