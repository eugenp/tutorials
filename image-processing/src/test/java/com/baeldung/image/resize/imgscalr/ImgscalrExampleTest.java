package com.baeldung.image.resize.imgscalr;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class ImgscalrExampleTest {
    @Test
    public void whenOriginalImageExistsAndTargetSizesAreNotZero_thenImageGeneratedWithoutError() throws IOException {
        int targetWidth = 200;
        int targetHeight = 200;
        BufferedImage outputImage = null;
        boolean errorThrown = false;
        BufferedImage originalImage = ImageIO.read(new File("src/main/resources/images/sampleImage.jpg"));
        try {
            outputImage = ImgscalrExample.resizeImage(originalImage, targetWidth, targetHeight);
        } catch (Exception e) {
            errorThrown = true;
        }

        assertFalse(errorThrown);
        assertNotNull(outputImage);
    }

    @Test
    public void whenOriginalImageExistsAndTargetSizesAreNotZero_thenOutputImageSizeIsValid() throws Exception {
        int targetWidth = 200;
        int targetHeight = 200;
        boolean errorThrown = false;
        BufferedImage outputImage = null;
        BufferedImage originalImage = ImageIO.read(new File("src/main/resources/images/sampleImage.jpg"));
        assertNotEquals(originalImage.getWidth(), targetWidth);
        assertNotEquals(originalImage.getHeight(), targetHeight);
        try {
            outputImage = ImgscalrExample.resizeImage(originalImage, targetWidth, targetHeight);
        } catch (Exception e) {
            errorThrown = true;
        }

        assertFalse(errorThrown);
        assertEquals(outputImage.getWidth(), targetWidth);
        assertEquals(outputImage.getHeight(), targetHeight);
    }

    @Test
    public void whenTargetWidthIsZero_thenErrorIsThrown() throws IOException {
        int targetWidth = 0;
        int targetHeight = 200;
        boolean errorThrown = false;
        BufferedImage originalImage = ImageIO.read(new File("src/main/resources/images/sampleImage.jpg"));
        BufferedImage outputImage = null;
        try {
            outputImage = ImgscalrExample.resizeImage(originalImage, targetWidth, targetHeight);
        } catch (Exception e) {
            errorThrown = true;
        }

        assertTrue(errorThrown);
        assertNull(outputImage);
    }

    @Test
    public void whenTargetHeightIsZero_thenErrorIsThrown() throws IOException {
        int targetWidth = 200;
        int targetHeight = 0;
        boolean errorThrown = false;
        BufferedImage originalImage = ImageIO.read(new File("src/main/resources/images/sampleImage.jpg"));
        BufferedImage outputImage = null;
        try {
            outputImage = ImgscalrExample.resizeImage(originalImage, targetWidth, targetHeight);
        } catch (Exception e) {
            errorThrown = true;
        }

        assertTrue(errorThrown);
        assertNull(outputImage);
    }

    @Test
    public void whenOriginalImageDoesNotExist_thenErrorIsThrown() throws IOException {
        int targetWidth = 200;
        int targetHeight = 200;
        boolean errorThrown = false;
        BufferedImage outputImage = null;
        try {
            outputImage = ImgscalrExample.resizeImage(null, targetWidth, targetHeight);
        } catch (Exception e) {
            errorThrown = true;
        }

        assertTrue(errorThrown);
        assertNull(outputImage);
    }
}
