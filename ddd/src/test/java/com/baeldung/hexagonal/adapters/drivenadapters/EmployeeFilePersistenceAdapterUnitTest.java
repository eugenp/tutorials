package com.baeldung.hexagonal.adapters.drivenadapters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.hexagonal.application.entities.Employee;

/**
 * @author Hesam Ghiasi created on 2/15/22 
 */
public class EmployeeFilePersistenceAdapterUnitTest {

    private EmployeeFilePersistenceAdapter employeeFilePersistenceAdapter;

    @BeforeEach
    void setup() {
        employeeFilePersistenceAdapter = new EmployeeFilePersistenceAdapter();
    }

    @Test
    public void shoudCreateFile() {
        Employee employee = new Employee();
        employee.setAge(10);
        employee.setName("John");
        String expectedContent = "{" + "name=" + employee.getName() + ", age=" + employee.getAge() + '}';
        String path = employeeFilePersistenceAdapter.saveEmployee(employee);
        File file = new File(path);
        try {
            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String actualContent = br.readLine();

            assertEquals(expectedContent, actualContent, "Content not the same");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
