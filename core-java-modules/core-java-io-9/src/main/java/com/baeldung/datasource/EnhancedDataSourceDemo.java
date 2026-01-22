package com.baeldung.datasource;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * Enhanced DataSource with content type support
 */
class InputStreamDataSource implements DataSource {

    private final InputStream inputStream;
    private final String contentType;

    public InputStreamDataSource(InputStream inputStream, String contentType) {
        this.inputStream = inputStream;
        this.contentType = contentType;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return inputStream;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public String getName() {
        return "InputStreamDataSource";
    }
}

/**
 * Demo class showing different content types
 */
public class EnhancedDataSourceDemo {

    public static void main(String[] args) {
        try {
            // Example 1: PDF document
            System.out.println("Example 1: PDF Document");
            InputStream pdfStream =
                new ByteArrayInputStream("PDF content here".getBytes());

            DataHandler pdfHandler =
                new DataHandler(
                    new InputStreamDataSource(pdfStream, "application/pdf")
                );

            System.out.println("Content type: " + pdfHandler.getContentType());
            System.out.println();

            // Example 2: JPEG image
            System.out.println("Example 2: JPEG Image");
            InputStream imageStream =
                new ByteArrayInputStream("Image data here".getBytes());

            DataHandler imageHandler =
                new DataHandler(
                    new InputStreamDataSource(imageStream, "image/jpeg")
                );

            System.out.println("Content type: " + imageHandler.getContentType());
            System.out.println();

            // Example 3: Plain text
            System.out.println("Example 3: Plain Text");
            InputStream textStream =
                new ByteArrayInputStream("Text content".getBytes());

            DataHandler textHandler =
                new DataHandler(
                    new InputStreamDataSource(textStream, "text/plain")
                );

            System.out.println("Content type: " + textHandler.getContentType());

            System.out.println(
                "\n✓ All DataHandlers created with specific content types!"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
