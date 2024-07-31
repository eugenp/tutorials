package com.baeldung.imageprocessing.openimaj;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.math.geometry.point.Point2d;
import org.openimaj.math.geometry.point.Point2dImpl;
import org.openimaj.math.geometry.shape.Polygon;

import java.io.IOException;
import java.util.Arrays;

public class OpenIMAJRectExample {
    public static void main(String[] args) throws IOException {
        MBFImage image = ImageUtilities.readMBF(OpenIMAJRectExample.class.getClassLoader().getResource("lena.jpg"));
        drawRectangle(image);
        DisplayUtilities.display(image);
    }

    private static void drawRectangle(MBFImage image) {
        Point2d tl = new Point2dImpl(10, 10);
        Point2d bl = new Point2dImpl(10, image.getHeight() - 10);
        Point2d br = new Point2dImpl(image.getWidth() - 10, image.getHeight() - 10);
        Point2d tr = new Point2dImpl(image.getWidth() - 10, 10);
        Polygon polygon = new Polygon(Arrays.asList(tl, bl, br, tr));
        image.drawPolygon(polygon, 4, new Float[] { 0f, 0f, 255.0f });
    }
}
