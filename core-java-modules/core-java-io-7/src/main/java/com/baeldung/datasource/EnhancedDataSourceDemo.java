package com.baeldung.datasource;

import javax.activation.DataHandler;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class EnhancedDataSourceDemo {

    public static void main(String[] args) {

        InputStream pdfStream = new ByteArrayInputStream("PDF content here".getBytes());

        DataHandler pdfHandler = new DataHandler(new InputStreamDataSource(pdfStream, "application/pdf"));

        System.out.println("Content type: " + pdfHandler.getContentType());
    }
}