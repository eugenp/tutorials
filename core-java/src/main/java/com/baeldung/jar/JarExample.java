package com.baeldung.jar;

import java.io.IOException;
import java.util.List;

public class JarExample {
    private static final String DIMENSION_FILE = "/dimensions.txt";

    public static void main(String[] args) {
        String inputType = System.getProperty("input");
        if (inputType != null && inputType.equalsIgnoreCase("file")) {
            Dimensioner dimensioner = new Dimensioner();
            try {
                List<Rectangle> rectangles = dimensioner.loadFromFile(DIMENSION_FILE);
                rectangles.forEach(rectangle -> {
                    rectangle.printArea();
                    rectangle.printPerimeter();
                });
            } catch (IOException e) {
                System.err.println("Exception loading dimensions");
            }
        } else if (args.length > 0) {
            int length = Integer.valueOf(args[0]);
            int width = (args.length > 1) ? Integer.valueOf(args[1]) : Integer.valueOf(args[0]);
            Rectangle rectangle = new Rectangle(length, width);
            rectangle.printArea();
            rectangle.printPerimeter();
        }
    }

}
