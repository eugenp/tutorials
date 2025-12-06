package com.baeldung.resetinputstream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

class whenByteArrayInputStream_thenMarkSupported {

    @Test
    void test() {
        byte[] buffer = { 1, 2, 3, 4, 5 };
        ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
        boolean isMarkSupported = bis.markSupported();
        assertEquals(isMarkSupported, true);
    }

}
