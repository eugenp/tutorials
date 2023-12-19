package com.baeldung.compressbytes;

import org.junit.jupiter.api.Test;

import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompressByteArrayUnitTest {

    private static final String INPUT_STRING = "Building a REST API is not a trivial task – from the high-level RESTful " +
            "constraints down to the nitty-gritty of making everything work and work well." +
            "Spring has made REST a first-class citizen and the platform has been maturing in leaps and bounds." +
            "With this guide, my aim is to organize the mountains of information that are available on the subject and " +
            "guide you through properly building an API." +
            "The guide starts with the basics – bootstrapping the REST API, the Spring MVC Configuration, and basic customization.";

    @Test
    void givenInputString_whenCompressWithDefaultLevel_thenDecompressWithSameSize() throws DataFormatException {
        byte[] input = INPUT_STRING.getBytes();
        byte[] compressedData = CompressByteArrayUtil.compress(input);
        byte[] decompressedData = CompressByteArrayUtil.decompress(compressedData);
        System.out.println("Original: " + input.length + " bytes");
        System.out.println("Compressed: " + compressedData.length + " bytes");
        System.out.println("Decompressed: " + decompressedData.length + " bytes");
        assertEquals(input.length, decompressedData.length);
    }

    @Test
    void givenInputString_whenCompressWithCustomLevel_thenDecompressWithSameSize() throws DataFormatException {
        byte[] input = INPUT_STRING.getBytes();
        byte[] compressedData = CompressByteArrayUtil.compressWithCustomLevel(input, 1);
        byte[] decompressedData = CompressByteArrayUtil.decompress(compressedData);
        System.out.println("Original: " + input.length + " bytes");
        System.out.println("Compressed: " + compressedData.length + " bytes");
        System.out.println("Decompressed: " + decompressedData.length + " bytes");
        assertEquals(input.length, decompressedData.length);
    }
}
