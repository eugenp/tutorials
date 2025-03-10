package com.baeldung.componentscanautoconfigure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.baeldung.componentscanautoconfigure.teacher.Teacher;

@ComponentScan(basePackages = {"com.baeldung.componentscanautoconfigure.healthcare",
        "com.baeldung.componentscanautoconfigure.employee"},
        basePackageClasses = Teacher.class)
@EnableAutoConfiguration(exclude = {JdbcTemplateAutoConfiguration.class})
//@EnableAutoConfiguration(excludeName = {"org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration"})
public class EmployeeApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }
}
