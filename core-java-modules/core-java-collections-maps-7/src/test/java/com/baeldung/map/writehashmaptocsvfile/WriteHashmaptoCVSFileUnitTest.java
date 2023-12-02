package com.baeldung.writehashmaptocsvfile;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WriteHashmaptoCVSFileUnitTest {
    public Map<String, String> employeeData;

    public WriteHashmaptoCVSFileUnitTest() {
        employeeData = new HashMap<>();
        employeeData.put("Name", "John Doe");
        employeeData.put("Title", "Software Engineer");
        employeeData.put("Department", "Engineering");
        employeeData.put("Salary", "75000");
    }

    @Test
    public void givenEmployeeData_whenWriteToCSVUsingFileWriter_thenCSVFileIsCreated() {

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
    public void givenCSVFile_whenWriteToCSVUsingApacheCommons_thenContentsMatchExpected() {

        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter("employee_data2.csv"), CSVFormat.DEFAULT)) {
            // Write header row
            csvPrinter.printRecord("Name", "Title", "Department", "Salary");

            // Write data row
            csvPrinter.printRecord(employeeData.get("Name"), employeeData.get("Title"), employeeData.get("Department"), employeeData.get("Salary"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ensure the CSV file exists
        assertTrue(new File("employee_data2.csv").exists());
    }
}
