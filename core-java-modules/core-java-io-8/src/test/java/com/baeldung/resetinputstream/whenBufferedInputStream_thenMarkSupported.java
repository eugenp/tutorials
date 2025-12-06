package com.baeldung.resetinputstream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class whenBufferedInputStream_thenMarkSupported {

    final String fileName = "src/test/resources/InputStreamData.txt";

    @Test
    void test() throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName));
        boolean isMarkSupported = bis.markSupported();
        assertEquals(isMarkSupported, true);
    }
}
