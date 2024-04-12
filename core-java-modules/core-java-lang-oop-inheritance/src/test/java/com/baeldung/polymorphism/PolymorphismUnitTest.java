package com.baeldung.polymorphism;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import org.junit.Test;

public class PolymorphismUnitTest {

    @Test
    public void givenImageFile_whenFileCreated_shouldSucceed() {
        ImageFile imageFile = FileManager.createImageFile("SampleImageFile", 200, 100, new BufferedImage(100, 200, BufferedImage.TYPE_INT_RGB).toString()
            .getBytes(), "v1.0.0");
        assertEquals(200, imageFile.getHeight());
    }

    // Downcasting then Upcasting
    @Test
    public void givenTextFile_whenTextFileCreatedAndAssignedToGenericFileAndCastBackToTextFileOnGetWordCount_shouldSucceed() {
        GenericFile textFile = FileManager.createTextFile("SampleTextFile", "This is a sample text content", "v1.0.0");
        TextFile textFile2 = (TextFile) textFile;
        assertEquals(6, textFile2.getWordCount());
    }

    // Downcasting
    @Test(expected = ClassCastException.class)
    public void givenGenericFile_whenCastToTextFileAndInvokeGetWordCount_shouldFail() {
        GenericFile genericFile = new GenericFile();
        TextFile textFile = (TextFile) genericFile;
        System.out.println(textFile.getWordCount());
    }
}
