package com.baeldung.polymorphism;

import java.awt.image.BufferedImage;

import org.junit.Test;

public class PolymorphismUnitTest {

    @Test
    public void givenImageFile_whenFileCreated_shouldSucceed() {
        FileManager.createImageFile("SampleImageFile", 200, 100, new BufferedImage(100, 200, BufferedImage.TYPE_INT_RGB).toString()
            .getBytes(), "v1.0.0");
    }

    @Test
    public void givenTextFile_whenTextFileCreatedAndAssignedToGenericFile_shouldSucceed() {
        FileManager.createTextFile("SampleTextFile", "This is a sample text content", "v1.0.0");
    }

}
