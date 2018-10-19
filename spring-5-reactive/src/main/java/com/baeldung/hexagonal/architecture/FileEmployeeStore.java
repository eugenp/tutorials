package com.baeldung.hexagonal.architecture;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.baeldung.hexagonal.architecture.core.Employee;
import com.baeldung.hexagonal.architecture.core.EmployeeStore;

public class FileEmployeeStore implements EmployeeStore {

    String filePath;

    public FileEmployeeStore(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Employee> getEmployees() {

        List<Employee> employees = new ArrayList<Employee>();

        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            String line = in.readLine(); // skip header
            while ((line = in.readLine()) != null) {
                Employee employee = mapEmployee(line);
                employees.add(employee);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return employees;
    }

    private Employee mapEmployee(String line) {
        String[] data = line.split(",");
        return new Employee(Integer.parseInt(data[0]), data[1], LocalDate.parse(data[2], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }

}
