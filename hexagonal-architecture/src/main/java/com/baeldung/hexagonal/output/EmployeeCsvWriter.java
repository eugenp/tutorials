package com.baeldung.hexagonal.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.baeldung.hexagonal.domain.Employee;

public class EmployeeCsvWriter implements EmployeeOutput {
    private File outputFile;
    
    public EmployeeCsvWriter(String path, String fileName) throws IllegalArgumentException {
        if (fileName == null || path == null || fileName.length() == 0 || path.length() == 0) {
            throw new IllegalArgumentException("Path and FileName are required");
        } else if (!fileName.endsWith(".csv")) {
            throw new IllegalArgumentException("File name must be a .csv file");
        }
        
        System.out.println(path);
        if (!path.endsWith("/")) path += "/";
        
        outputFile = new File(path, fileName);
    }

    @Override
    public void writeAll(List<Employee> employees) {
        BufferedWriter writer = null;
        
        try {
            writer = new BufferedWriter(new FileWriter(outputFile));
            for (Iterator<Employee> it = employees.iterator(); it.hasNext();) {
                Employee emp = it.next();
                StringBuffer empLine = new StringBuffer();
                empLine.append(emp.getId());
                empLine.append(",");
                empLine.append(emp.getFirstName());
                empLine.append(",");
                empLine.append(emp.getLastName());
                empLine.append(",");
                empLine.append(emp.getEmployeeId());
                empLine.append(",");
                empLine.append(emp.getSalary());
                writer.write(empLine.toString());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException ioe) {
            //handle the exception
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    //handle the exception
                }
            }
        }

    }

}
