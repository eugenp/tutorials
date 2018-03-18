package com.baeldung.beaninjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = { DepartmentService.class })
public class OrganizationConfiguration {
    @Bean
    public Department accountsDept() {
        return new Department(1, "Accounts");
    }

    @Bean
    public Department computerDept() {
        return new Department(1, "Computer");
    }

    @Bean
    public Employee itEnigneer(Department computerDept) {
        return new Employee("Bob", computerDept);
    }

}
