package com.bealdung.java9.finalizers;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FinalizeUnitTest {

    @Test
    void givenFilename_whenGetLineNumber_thenReturnCorrectNumber() throws IOException {
        final File tmpFile = File.createTempFile("test", ".tmp");
        final BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile));
        writer.write("Baeldung");
        writer.close();

        long lineNumber = 0;
        try (Resource resource = new Resource(tmpFile.getAbsolutePath())) {
            lineNumber = resource.getLineNumber();
        } catch (Exception e) {
            System.err.println("Error " + e);
        }

        assertEquals(1, lineNumber);
    }
}
