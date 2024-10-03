package com.baeldung.azure.functions;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.azure.functions.entity.Employee;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public Function<Employee, Employee> employeeSalaryFunction() {
        return new EmployeeSalaryFunction();
    }

    @Bean
    public Function<Employee, Employee> defaultSalaryCalculatorFn() {
        return new DefaultSalaryCalculatorFn();
    }

    @Bean
    public Function<Employee, Employee> californiaSalaryCalculatorFn() {
        return new CaliforniaSalaryCalculatorFn();
    }

    @Bean
    public Function<Employee, Employee> chicagoSalaryCalculatorFn() {
        return new ChicagoSalaryCalculatorFn();
    }

    @Bean
    public Function<Employee, Employee> newYorkSalaryCalculatorFn() {
        return new NewYorkSalaryCalculatorFn();
    }
}
