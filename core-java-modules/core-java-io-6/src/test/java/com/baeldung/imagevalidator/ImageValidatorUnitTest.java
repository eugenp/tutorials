package com.baeldung.imagevalidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ImageValidatorUnitTest {

    @Test
    public void givenImageFile_whenCheckedUsingTika_thenShouldReturnTrue() throws IOException {
        File file = new File("src/test/resources/test-image.jpg");
        assertTrue(ImageValidator.isImageFileUsingTika(file));
    }

    @Test
    public void givenFakeImageFile_whenCheckedUsingTika_thenShouldReturnFalse() throws IOException {
        File file = new File("src/test/resources/fake-image.jpg");
        assertFalse(ImageValidator.isImageFileUsingTika(file));
    }

    @Test
    public void givenImageFile_whenCheckedUsingImageIO_thenShouldReturnTrue() throws IOException {
        File file = new File("src/test/resources/test-image.jpg");
        assertTrue(ImageValidator.isImageFileUsingImageIO(file));
    }

    @Test
    public void givenFakeImageFile_whenCheckedUsingImageIO_thenShouldReturnFalse() throws IOException {
        File file = new File("src/test/resources/fake-image.jpg");
        assertFalse(ImageValidator.isImageFileUsingImageIO(file));
    }

    @Test
    public void givenImageFile_whenCheckedUsingProbeContentType_thenShouldReturnTrue() throws IOException {
        File file = new File("src/test/resources/test-image.jpg");
        assertTrue(ImageValidator.isImageFileUsingProbeContentType(file));
    }

    @Test
    public void givenNonImageFile_whenCheckedUsingProbeContentType_thenShouldReturnFalse() throws IOException {
        File file = new File("src/test/resources/test-document.txt");
        assertFalse(ImageValidator.isImageFileUsingProbeContentType(file));
    }

    @Test
    public void givenFakeImageFile_whenCheckedUsingProbeContentType_thenShouldReturnTrue() throws IOException {
        File file = new File("src/test/resources/fake-image.jpg");
        assertTrue(ImageValidator.isImageFileUsingProbeContentType(file));
    }

}
