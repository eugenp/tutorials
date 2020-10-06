package com.baeldung.annotations;

import com.baeldung.annotations.componentscanautoconfigure.teacher.Teacher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.baeldung.annotations.componentscanautoconfigure.healthcare",
        "com.baeldung.annotations.componentscanautoconfigure.employee"},
        basePackageClasses = Teacher.class)
@EnableAutoConfiguration(exclude = {JdbcTemplateAutoConfiguration.class})
//@EnableAutoConfiguration(excludeName = {"org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration"})
public class EmployeeApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(EmployeeApplication.class, args);
        System.out.println("Configures Employee: " + context.containsBeanDefinition("employee"));
        System.out.println("Configures Senior Employee: " + context.containsBeanDefinition("seniorEmployee"));
        System.out.println("Configures Doctor: " + context.containsBeanDefinition("doctor"));
        System.out.println("Configures Hospital: " + context.containsBeanDefinition("hospital"));
        System.out.println("Configures Student: " + context.containsBeanDefinition("student"));
        System.out.println("Configures Teacher: " + context.containsBeanDefinition("teacher"));
    }
}
