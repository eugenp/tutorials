package com.baeldung.imageprocessing.imagej;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.awt.*;

public class ImageJRectExample {
    public static void main(String[] args) {
        ImagePlus imp = IJ.openImage(ImageJRectExample.class.getClassLoader().getResource("lena.jpg").getPath());
        drawRect(imp);
        imp.show();
    }

    private static void drawRect(ImagePlus imp) {
        ImageProcessor ip = imp.getProcessor();
        ip.setColor(Color.BLUE);
        ip.setLineWidth(4);
        ip.drawRect(10, 10, imp.getWidth() - 20, imp.getHeight() - 20);
    }
}
