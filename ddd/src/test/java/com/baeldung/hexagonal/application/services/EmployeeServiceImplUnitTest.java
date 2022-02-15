package com.baeldung.hexagonal.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.hexagonal.adapters.drivenadapters.EmployeeFilePersistenceAdapter;
import com.baeldung.hexagonal.ports.drivenports.EmployeePersistence;
import com.baeldung.hexagonal.ports.driverports.EmployeeService;

/**
 * @author Hesam Ghiasi created on 2/15/22 
 */
public class EmployeeServiceImplUnitTest {

    private EmployeePersistence employeePersistence;
    private EmployeeService employeeService;

    @BeforeEach
    void setup() {
        employeePersistence = new EmployeeFilePersistenceAdapter();
        employeeService = new EmployeeServiceImpl(employeePersistence);
    }

    @Test
    public void shouldCreateEmployee_thenSaveIt() {
        String path = employeeService.addEmployee("John", 1990);
        int currentYear = Calendar.getInstance()
            .get(Calendar.YEAR);
        String expectedContent = "{" + "name=" + "John" + ", age=" + (currentYear - 1990) + '}';
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
