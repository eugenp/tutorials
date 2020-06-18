package com.baeldung.image.resize.imgscalr;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class ImgscalrExampleTest {

    @Test(expected = Test.None.class)
    public void whenOriginalImageExistsAndTargetSizesAreNotZero_thenImageGeneratedWithoutError() throws IOException {
        int targetWidth = 200;
        int targetHeight = 200;
        BufferedImage originalImage = ImageIO.read(new File("src/main/resources/images/sampleImage.jpg"));
        BufferedImage outputImage = ImgscalrExample.resizeImage(originalImage, targetWidth, targetHeight);

        assertNotNull(outputImage);
    }

    @Test(expected = Test.None.class)
    public void whenOriginalImageExistsAndTargetSizesAreNotZero_thenOutputImageSizeIsValid() throws IOException {
        int targetWidth = 200;
        int targetHeight = 200;
        BufferedImage originalImage = ImageIO.read(new File("src/main/resources/images/sampleImage.jpg"));
        assertNotEquals(originalImage.getWidth(), targetWidth);
        assertNotEquals(originalImage.getHeight(), targetHeight);
        BufferedImage outputImage = ImgscalrExample.resizeImage(originalImage, targetWidth, targetHeight);

        assertEquals(outputImage.getWidth(), targetWidth);
        assertEquals(outputImage.getHeight(), targetHeight);
    }

    @Test(expected = Test.None.class)
    public void whenTargetWidthIsZero_thenImageIsCreated() throws IOException {
        int targetWidth = 0;
        int targetHeight = 200;
        BufferedImage originalImage = ImageIO.read(new File("src/main/resources/images/sampleImage.jpg"));
        BufferedImage outputImage = ImgscalrExample.resizeImage(originalImage, targetWidth, targetHeight);

        assertNotNull(outputImage);
    }

    @Test(expected = Test.None.class)
    public void whenTargetHeightIsZero_thenImageIsCreated() throws IOException {
        int targetWidth = 200;
        int targetHeight = 0;
        BufferedImage originalImage = ImageIO.read(new File("src/main/resources/images/sampleImage.jpg"));
        BufferedImage outputImage = ImgscalrExample.resizeImage(originalImage, targetWidth, targetHeight);

        assertNotNull(outputImage);
    }

    @Test(expected = Exception.class)
    public void whenOriginalImageDoesNotExist_thenErrorIsThrown() {
        int targetWidth = 200;
        int targetHeight = 200;
        BufferedImage outputImage = ImgscalrExample.resizeImage(null, targetWidth, targetHeight);

        assertNull(outputImage);
    }
}
