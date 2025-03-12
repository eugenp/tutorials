package com.baeldung.compress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

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