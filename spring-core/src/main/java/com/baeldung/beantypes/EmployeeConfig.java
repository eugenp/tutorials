/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.beantypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author tehreem.nisa
 */
@Configuration
public class EmployeeConfig {

    @Bean
    public Employee Manager() {
        return new Employee(10000,"Manager");
    }

    @Bean
    public Employee Object() {
        Employee e = new Employee();
        e.setsalary(20000);
        e.settitle("Consultant");
        return e;
    }
}
