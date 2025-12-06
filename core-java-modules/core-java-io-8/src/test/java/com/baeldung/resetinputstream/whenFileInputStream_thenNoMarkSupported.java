package com.baeldung.resetinputstream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class whenFileInputStream_thenNoMarkSupported {

    final String fileName = "src/test/resources/InputStreamData.txt";

    @Test
    void test() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        boolean isMarkSupported = fis.markSupported();
        assertEquals(isMarkSupported, false);
    }

}
