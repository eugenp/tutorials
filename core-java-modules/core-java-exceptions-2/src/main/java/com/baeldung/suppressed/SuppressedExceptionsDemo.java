package com.baeldung.suppressed;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SuppressedExceptionsDemo {

    public static void demoSuppressedException(String filePath) throws IOException {
        FileInputStream fileIn = null;
        try {
            fileIn = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            throw new IOException(e);
        } finally {
            fileIn.close();
        }
    }
    
    public static void demoAddSuppressedException(String filePath) throws IOException {
        Throwable firstException = null;
        FileInputStream fileIn = null;
        try {
            fileIn = new FileInputStream(filePath);
        } catch (IOException e) {
            firstException = e;
        } finally {
            try {
                fileIn.close();
            } catch (NullPointerException npe) {
                if (firstException != null) {
                    npe.addSuppressed(firstException);
                }
                throw npe;
            }
        }
    }
    
    public static void demoExceptionalResource() throws Exception {
        try (ExceptionalResource exceptionalResource = new ExceptionalResource()) {
            exceptionalResource.processSomething();
        }
    }
}
