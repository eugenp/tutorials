package org.baeldung.core.exceptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;

public class FileNotFoundExceptionTest {

    private static final Logger LOG = Logger.getLogger(FileNotFoundExceptionTest.class);

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
                System.out.println("File was created and read.");
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
            System.out.println("File was logged.");
        }
    }
    
    protected void readFailingFile() throws IOException {
        BufferedReader rd = null;
        rd = new BufferedReader(new FileReader(new File(fileName)));
        rd.readLine();
        // no need to close file
    }

    class BusinessException extends RuntimeException {
        public BusinessException(String string, FileNotFoundException ex) {
            super(string, ex);
        }
    }
}
