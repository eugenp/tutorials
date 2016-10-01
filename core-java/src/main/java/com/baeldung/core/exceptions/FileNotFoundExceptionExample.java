package com.baeldung.core.exceptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

public class FileNotFoundExceptionExample {

    private static final Logger LOG = Logger.getLogger(FileNotFoundExceptionExample.class);

    private String fileName = Double.toString(Math.random());

    public void readFailingFile() throws IOException {
        BufferedReader rd = null;
        rd = new BufferedReader(new FileReader(new File(fileName)));
        rd.readLine();
        // no need to close file
    }

    public void raiseBusinessSpecificException() throws IOException {
        try {
            readFailingFile();
        } catch (FileNotFoundException ex) {
            throw new BusinessException("BusinessException: necessary file was not present.", ex);
        }
    }

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

    public void logError() throws IOException {
        try {
            readFailingFile();
        } catch (FileNotFoundException ex) {
            LOG.error("Optional file " + fileName + " was not found.", ex);
            System.out.println("File was logged.");
        }
    }

    public static void main(String[] args) throws IOException {
        FileNotFoundExceptionExample example = new FileNotFoundExceptionExample();

        try {
            example.raiseBusinessSpecificException();
        } catch (Exception e) {
            System.out.println("Bussines exception was thrown");
        }
        example.createFile();
        example.logError();
    }

    class BusinessException extends RuntimeException {

        public BusinessException(String string, FileNotFoundException ex) {
            super(string, ex);
        }

    }
}