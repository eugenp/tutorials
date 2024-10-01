package com.baeldung.azure.functions;

import java.util.function.Function;

import org.springframework.cloud.function.context.FunctionCatalog;

import com.baeldung.azure.functions.entity.Employee;


public class EmployeeSalaryFunctionWrapper {

    private FunctionCatalog functionCatalog;

    public EmployeeSalaryFunctionWrapper(FunctionCatalog functionCatalog) {
        this.functionCatalog = functionCatalog;
    }

    public Function<Employee, Employee> getCityBasedSalaryFunction(Employee employee) {
        Function<Employee, Employee> salaryCalculatorFunction;
        switch (employee.getCity()) {
            case "Chicago" -> salaryCalculatorFunction = functionCatalog.lookup("chicagoSalaryCalculatorFn");
            case "California" -> salaryCalculatorFunction = functionCatalog.lookup("californiaSalaryCalculatorFn|defaultSalaryCalculatorFn");
            case "New York" -> salaryCalculatorFunction = functionCatalog.lookup("newYorkSalaryCalculatorFn");
            default -> salaryCalculatorFunction = functionCatalog.lookup("defaultSalaryCalculatorFn");
        }
        return salaryCalculatorFunction;
    }
}
