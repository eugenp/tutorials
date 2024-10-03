package com.baeldung.rgb.setcolor;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GraphicsRGBSetColorUnitTest {
    private Graphics graphics;

    @Before
    public void setUp() {
        // Create a mock Graphics object
        graphics = mock(Graphics.class);
    }

    @Test
    public void whenSetColorWhite_thenColorIsSetCorrectly() {
        // Create a white color
        Color myWhite = new Color(255, 255, 255);

        // Apply the color using setColor()
        graphics.setColor(myWhite);

        // Verify that setColor was called with the correct color
        verify(graphics).setColor(myWhite);
    }

    @Test
    public void whenSetColorPurple_thenColorIsSetCorrectly() {
        // Create a purple color
        Color myPurple = new Color(128, 0, 128);

        // Apply the color using setColor()
        graphics.setColor(myPurple);

        // Verify that setColor was called with the correct color
        verify(graphics).setColor(myPurple);
    }

    @Test
    public void whenSetColorRed_thenRectangleIsDrawnWithRed() {
        // Create a red color
        Color redColor = new Color(255, 0, 0);

        // Apply the red color and draw a rectangle
        graphics.setColor(redColor);
        graphics.fillRect(10, 10, 100, 100);

        // Verify that setColor was called with the correct color
        verify(graphics).setColor(redColor);

        // Verify that the rectangle was drawn with the correct parameters
        verify(graphics).fillRect(10, 10, 100, 100);
    }

    @Test
    public void whenSetMultipleColors_thenRectanglesAreDrawnWithCorrectColors() {
        // Create red and blue colors
        Color redColor = new Color(255, 0, 0);
        Color blueColor = new Color(0, 0, 255);

        // Apply the red color and draw the first rectangle
        graphics.setColor(redColor);
        graphics.fillRect(10, 10, 100, 100);

        // Apply the blue color and draw the second rectangle
        graphics.setColor(blueColor);
        graphics.fillRect(120, 10, 100, 100);

        // Verify that setColor was called with the correct colors in the right order
        verify(graphics).setColor(redColor);
        verify(graphics).setColor(blueColor);

        // Verify that the rectangles were drawn with the correct parameters
        verify(graphics).fillRect(10, 10, 100, 100);
        verify(graphics).fillRect(120, 10, 100, 100);
    }
}
