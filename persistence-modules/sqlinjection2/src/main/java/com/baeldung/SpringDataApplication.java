package com.baeldung;

import com.baeldung.model.Employee;
import com.baeldung.springdata.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDataApplication {

        public static void main(String[] args) {

                EmployeeRepository repository = SpringApplication.run(SpringDataApplication.class, args).getBean(EmployeeRepository.class);

                // search by last name
                for (Employee emp : repository.findByName("Trudy")) {
                        System.out.println(emp);
                }

                // search by last name
                for (Employee emp : repository.findByNameQueryInSecure("Trudy","asc' when 1=1")) {
                        System.out.println(emp);
                }
        }

}
