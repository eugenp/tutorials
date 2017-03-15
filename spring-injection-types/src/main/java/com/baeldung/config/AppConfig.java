package com.baeldung.config;

import com.baeldung.model.Address;
import com.baeldung.model.Department;
import com.baeldung.model.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Address officeAddress(){
        return new Address("USA", "CA", "ABC", "Street 1", "94522");
    }

    @Bean
    public Employee defaultEmployee(){
        Employee emp =  new Employee();
        emp.setFirstName("Emily");
        emp.setLastName("Johnson");
        emp.setDepartment(Department.SALES);
        return emp;
    }
}
