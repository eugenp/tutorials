package org.baeldung.core.exceptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Test;

public class FileNotFoundExceptionUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(FileNotFoundExceptionUnitTest.class);

    private String fileName = Double.toString(Math.random());

    @Test(expected = BusinessException.class)
    public void raiseBusinessSpecificException() throws IOException {
        try {
            readFailingFile();
        } catch (FileNotFoundException ex) {
            throw new BusinessException("BusinessException: necessary file was not present.", ex);
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
                throw new RuntimeException("BusinessException: even creation is not possible.", ioe);
            }
        }
    }

    @Test
    public void logError() throws IOException {
        try {
            readFailingFile();
        } catch (FileNotFoundException ex) {
            LOG.error("Optional file " + fileName + " was not found.", ex);
        }
    }

    private void readFailingFile() throws IOException {
        BufferedReader rd = new BufferedReader(new FileReader(new File(fileName)));
        rd.readLine();
        // no need to close file
    }

    private class BusinessException extends RuntimeException {
        BusinessException(String string, FileNotFoundException ex) {
            super(string, ex);
        }
    }
}
