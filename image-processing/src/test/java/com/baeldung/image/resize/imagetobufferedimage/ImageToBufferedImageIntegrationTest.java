package com.baeldung.image.resize.imagetobufferedimage;

import com.baeldung.imageprocessing.imagetobufferedimage.ImageToBufferedImage;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ImageToBufferedImageIntegrationTest {
    Image image = ImageIO.read(new File("src/main/resources/images/sampleImage.jpg"));

    public ImageToBufferedImageIntegrationTest() throws IOException {
    }

    @Test
    public void whenConvertUsingConstructorWithValidDimensions_thenImageGeneratedWithoutError() {
        ImageToBufferedImage converter = new ImageToBufferedImage();
        BufferedImage bufferedImage = converter.convertUsingConstructor(image);
        assertNotNull(bufferedImage);
        assertEquals(image.getWidth(null), bufferedImage.getWidth());
        assertEquals(image.getHeight(null), bufferedImage.getHeight());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenConvertUsingConstructorWithInvalidDimensions_thenImageGeneratedWithError() {
        ImageToBufferedImage converter = new ImageToBufferedImage();
        converter.convertUsingConstructor(new BufferedImage(-100, -100, BufferedImage.TYPE_INT_ARGB));
    }

    @Test
    public void whenConvertUsingCastingWithCompatibleImageType_thenImageGeneratedWithoutError() {
        ImageToBufferedImage converter = new ImageToBufferedImage();
        BufferedImage bufferedImage = converter.convertUsingCasting(image);
        assertNotNull(bufferedImage);
        assertEquals(image.getWidth(null), bufferedImage.getWidth());
        assertEquals(image.getHeight(null), bufferedImage.getHeight());
    }

    @Test(expected = ClassCastException.class)
    public void whenConvertUsingCastingWithIncompatibleImageType_thenImageGeneratedWithError() {
        ImageToBufferedImage converter = new ImageToBufferedImage();
        // PNG format is not directly supported by BufferedImage
        Image image = new ImageIcon("src/main/resources/images/baeldung.png").getImage();
        converter.convertUsingCasting(image);
    }
}

