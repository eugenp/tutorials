package com.baeldung.checksums;


import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ChecksumUtilsUnitTest {

    byte[] arr;

    @Before
    void setUp() {
        arr =  new byte[]{0,10,21,20,35,40,120,56,72,22};
    }

    @Test
    void givenByteArray_whenChecksumCreated_checkCorrect() {

        long checksum = ChecksumUtils.getChecksumCRC32(arr);

        assertEquals(3915397664L, checksum);
    }

    @Test
    void givenTwoDifferentStrings_whenChecksumCreated_checkCollision() {

        String plumless = "plumless";
        String buckeroo = "buckeroo";

        long plumlessChecksum = ChecksumUtils.getChecksumCRC32(plumless.getBytes());
        long buckerooChecksum = ChecksumUtils.getChecksumCRC32(buckeroo.getBytes());

        assertEquals(plumlessChecksum, buckerooChecksum);
    }

    @Test
    void givenInputString_whenChecksumCreated_checkCorrect() throws IOException {

        InputStream inputStream = new ByteArrayInputStream(arr);
        long checksum = ChecksumUtils.getChecksumCRC32(inputStream, 10);

        assertEquals(3915397664L, checksum);

    }
}