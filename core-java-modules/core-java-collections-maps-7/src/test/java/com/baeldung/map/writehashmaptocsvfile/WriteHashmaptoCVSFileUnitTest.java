package com.baeldung.writehashmaptocsvfile;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WriteHashmaptoCVSFileUnitTest {

    @Test
    public void givenEmployeeData_whenWriteToCSV_thenCSVFileIsCreated() {
        Map<String, String> employeeData = new HashMap<>();
        employeeData.put("Name", "John Doe");
        employeeData.put("Title", "Software Engineer");
        employeeData.put("Department", "Engineering");
        employeeData.put("Salary", "75000");
        try (FileWriter csvWriter = new FileWriter("employee_data.csv")) {
            // Write header row
            csvWriter.append("Name,Title,Department,Salary\n");

            // Write data row
            csvWriter.append(employeeData.get("Name")).append(",");
            csvWriter.append(employeeData.get("Title")).append(",");
            csvWriter.append(employeeData.get("Department")).append(",");
            csvWriter.append(employeeData.get("Salary")).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Ensure the CSV file exists
        assertTrue(new File("employee_data.csv").exists(), "CSV file does not exist!");

    }

    @Test
    public void givenCSVFile_whenRead_thenContentsMatchExpected() {
        // Read the actual content of the CSV file
        StringBuilder actualCsvContent = new StringBuilder();
        try {
            Files.lines(Paths.get("employee_data.csv"))
                    .forEach(line -> actualCsvContent.append(line).append("\n"));

            // Define the expected CSV content
            String expectedCsvContent = "Name,Title,Department,Salary\n" +
                    "John Doe,Software Engineer,Engineering,75000\n";

            // Compare the actual content with the expected content
            assertEquals(expectedCsvContent, actualCsvContent.toString());

            System.out.println("CSV file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
