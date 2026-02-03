package com.baeldung.datasource;

import javax.activation.DataHandler;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class DataSourceDemo {

    public static void main(String[] args) {

        try {
            String sampleData = "Hello from the database! This could be a large file.";
            InputStream inputStream =
                    new ByteArrayInputStream(sampleData.getBytes());

            System.out.println("Step 1: Retrieved InputStream from database");
            System.out.println("Data size: " + sampleData.length() + " bytes\n");

            DataHandler dataHandler = new DataHandler(
                    new InputStreamDataSource(inputStream, "application/octet-stream")
            );

            System.out.println("Step 2: Created DataHandler successfully!");
            System.out.println("Content type: " + dataHandler.getContentType());
            System.out.println("Data source name: " + dataHandler.getName() + "\n");

            InputStream resultStream = dataHandler.getInputStream();

            // Used only for demonstration – not memory efficient for large streams
            String retrievedData = new String(resultStream.readAllBytes());

            System.out.println("Step 3: Retrieved data from DataHandler:");
            System.out.println("\"" + retrievedData + "\"");

            System.out.println("\n✓ Success! Data streamed without loading entirely into memory first.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}