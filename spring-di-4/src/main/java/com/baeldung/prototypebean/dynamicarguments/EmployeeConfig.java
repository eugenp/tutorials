package com.baeldung.prototypebean.dynamicarguments;

import org.springframework.cglib.core.internal.Function;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = { "com.baeldung.prototypebean.dynamicarguments" })
public class EmployeeConfig {

    @Bean
    @Scope(value = "prototype")
    public Employee getEmployee(String name) {
        return new Employee(name);
    }

    @Bean
    public EmployeeBeanUsingObjectProvider employeeBeanUsingObjectProvider() {
        return new EmployeeBeanUsingObjectProvider();
    }

    @Bean
    public EmployeeBeanUsingObjectFactory employeeBeanUsingObjectFactory() {
        return new EmployeeBeanUsingObjectFactory();
    }

    @Bean
    public Function<String, Employee> beanFactory() {
        return name -> getEmployee(name);
    }

    @Bean
    public EmployeeBeanUsingFunction employeeBeanUsingFunction() {
        return new EmployeeBeanUsingFunction();
    }

}
