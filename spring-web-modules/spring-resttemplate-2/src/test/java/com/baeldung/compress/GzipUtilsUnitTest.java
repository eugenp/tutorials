package com.baeldung.compress;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GzipUtilsUnitTest {

    @Test
    public void givenCompressedText_whenDecompressed_thenSuccessful() throws Exception {
        final String expectedText = "Hello Baeldung!";
        byte[] compressedText = GzipUtils.compress(expectedText);
        String decompressedText = GzipUtils.decompress(compressedText);
        assertNotNull(compressedText);
        assertEquals(expectedText, decompressedText);
    }

}