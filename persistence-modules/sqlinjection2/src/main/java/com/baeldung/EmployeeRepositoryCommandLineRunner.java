package com.baeldung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.baeldung.model.Employee;
import com.baeldung.springdata.EmployeeRepository;

@SpringBootApplication
public class EmployeeRepositoryCommandLineRunner implements CommandLineRunner {

    @Autowired
    private EmployeeRepository empRepository;

    @Override
    public void run(String... arg0) throws Exception {
        // search by last name
        for (Employee emp : empRepository.findByName("Trudy")) {
            System.out.println(emp);
        }
    }
    
    public static void main(String[] args) throws Exception {

        SpringApplication.run(EmployeeRepositoryCommandLineRunner.class, args);

    }

}
