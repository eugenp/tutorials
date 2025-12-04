package com.baeldung.resetinputstream;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class whenFileInputStreamReset_thenIOException {

    final String fileName = "src/test/resources/InputStreamData.txt";
    final int readLimit = 500;

    @Test
    void test() {

        assertThrows(IOException.class, () -> {
            FileInputStream fis = new FileInputStream(fileName);
            fis.read();
            fis.mark(readLimit);
            fis.read();
            fis.reset();
        });
    }
}
