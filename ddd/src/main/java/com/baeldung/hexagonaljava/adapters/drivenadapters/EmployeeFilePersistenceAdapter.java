package com.baeldung.hexagonaljava.adapters.drivenadapters;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import com.baeldung.hexagonaljava.application.entities.Employee;
import com.baeldung.hexagonaljava.ports.drivenports.EmployeePersistence;

/**
 * @author Hesam Ghiasi created on 2/15/22 
 */
public class EmployeeFilePersistenceAdapter implements EmployeePersistence {
    @Override
    public String saveEmployee(Employee employee) {
        String path = Paths.get(this.getClass()
            .getResource("/")
            .getPath())
            .toAbsolutePath() + "/employees.txt";
        File file = new File(path);
        file.delete();
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(employee.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
