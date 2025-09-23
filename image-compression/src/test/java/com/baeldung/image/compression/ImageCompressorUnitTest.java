package com.baeldung.image.compression;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;


class ImageCompressorUnitTest {
    @Test
    void testSecondFileIsSmaller() throws IOException {
        String inputImagePath = ImageCompressorUnitTest.class.getClassLoader()
                .getResource("input.jpg").getPath();
        File inputImage = new File(inputImagePath);
        long inputImageSize = inputImage.length();
        String outputPath = inputImagePath + "compressed.jpg";
        ImageCompressor.compressImage(inputImagePath, outputPath);
        File compressedImage = new File(outputPath);
        long compressedImageSize = compressedImage.length();

        assertTrue(compressedImageSize < inputImageSize, "The compressed image is smaller in size");
    }
}