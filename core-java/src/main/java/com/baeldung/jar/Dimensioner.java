package com.baeldung.jar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Dimensioner {

    public List<Rectangle> loadFromFile(String dimensionFile) throws FileNotFoundException, IOException {
        List<Rectangle> rectangles = new ArrayList<Rectangle>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Dimensioner.class.getResourceAsStream(dimensionFile)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] dimensions = line.split(",");
                if (dimensions.length == 2) {
                    Rectangle rectangle = new Rectangle(Integer.valueOf(dimensions[0]), Integer.valueOf(dimensions[1]));
                    rectangles.add(rectangle);
                }
            }
        }
        return rectangles;
    }

}
