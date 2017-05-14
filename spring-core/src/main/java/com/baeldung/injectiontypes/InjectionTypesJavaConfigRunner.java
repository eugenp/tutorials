/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.baeldung.injectiontypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.baeldung.injectiontypes.domain.Student;
import com.baeldung.injectiontypes.javaconfig.InjectionTypesConfig;
import com.baeldung.injectiontypes.javaconfig.service.StudentService;

public class InjectionTypesJavaConfigRunner {

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(InjectionTypesConfig.class);
        StudentService serviceInjection = (StudentService) context.getBean("studentService");

        Student student = new Student("Alex", 25);
        serviceInjection.addStudent(student);

    }

}
