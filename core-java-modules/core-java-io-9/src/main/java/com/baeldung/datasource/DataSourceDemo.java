package com.baeldung.datasource;

import javax.activation.DataSource;
import javax.activation.DataHandler;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;

/**
 * Custom DataSource implementation backed by an InputStream
 */
class InputStreamDataSource implements DataSource {

    private final InputStream inputStream;

    public InputStreamDataSource(InputStream inputStream) {
        this.inputStream = inputStream;
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
        return "*/*";
    }

    @Override
    public String getName() {
        return "InputStreamDataSource";
    }
}

/**
 * Main demo class
 */
public class DataSourceDemo {

    public static void main(String[] args) {
        try {
            // Simulate getting data from database using getBinaryStream
            // In real code: resultSet.getBinaryStream(1)
            String sampleData =
                "Hello from the database! This could be a large file.";

            InputStream inputStream =
                new ByteArrayInputStream(sampleData.getBytes());

            System.out.println("Step 1: Retrieved InputStream from database");
            System.out.println("Data size: " + sampleData.length() + " bytes\n");

            // Create a DataHandler using the custom DataSource
            DataHandler dataHandler =
                new DataHandler(new InputStreamDataSource(inputStream));

            System.out.println("Step 2: Created DataHandler successfully!");
            System.out.println("Content type: " + dataHandler.getContentType());
            System.out.println("Data source name: " + dataHandler.getName() + "\n");

            // Retrieve and display the data
            InputStream resultStream = dataHandler.getInputStream();
            String retrievedData = new String(resultStream.readAllBytes());

            System.out.println("Step 3: Retrieved data from DataHandler:");
            System.out.println("\"" + retrievedData + "\"");

            System.out.println(
                "\n✓ Success! Data streamed without loading everything at once."
            );

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
