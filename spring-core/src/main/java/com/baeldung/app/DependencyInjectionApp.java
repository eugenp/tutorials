package com.baeldung.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.configuration.DependencyInjectionSpring;
import com.baeldung.domain.Course;
import com.baeldung.domain.Department;
import com.baeldung.domain.Employee;
import com.baeldung.domain.Student;

public class DependencyInjectionApp {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(DependencyInjectionSpring.class);

        System.out.println("Mandatory Dependency");
        Employee employee = context.getBean(Employee.class);
        employee.setId(1);
        employee.setName("Robert Langdon");
        Department department = context.getBean(Department.class);
        System.out.println("Department Name: "+department.getName());
        System.out.println("Employee in department: "+department.getEmployee().getName());
        
        System.out.println("Optional Dependency");
        Student student = context.getBean(Student.class);
        student.setId(1);
        student.setName("Albert Einstein");
        Course course = context.getBean(Course.class);
        System.out.println("Course:"+course.getName());
        System.out.println("Student enrolled: "+course.getStudent().getName());
        ((ConfigurableApplicationContext)context).close();
    }

}
