package com.baeldung.filenotfoundexception;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class FileNotFoundExceptionUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(FileNotFoundExceptionUnitTest.class);

    private String fileName = Double.toString(Math.random());

    @Test(expected = BusinessException.class)
    public void raiseBusinessSpecificException() throws IOException {
        try {
            readFailingFile();
        } catch (FileNotFoundException ex) {
            throw new BusinessException("BusinessException: necessary file was not present.");
        }
    }

    @Test
    public void createFile() throws IOException {
        try {
            readFailingFile();
        } catch (FileNotFoundException ex) {
            try {
                new File(fileName).createNewFile();
                readFailingFile();
            } catch (IOException ioe) {
                throw new RuntimeException("BusinessException: even creation is not possible.");
            }
        }
    }

    @Test
    public void logError() throws IOException {
        try {
            readFailingFile();
        } catch (FileNotFoundException ex) {
            LOG.error("Optional file " + fileName + " was not found.");
        }
    }

    private void readFailingFile() throws IOException {
        BufferedReader rd = new BufferedReader(new FileReader(new File(fileName)));
        rd.readLine();
        // no need to close file
    }

    private class BusinessException extends RuntimeException {
        BusinessException(String string) {
            super(string);
        }
    }
}
