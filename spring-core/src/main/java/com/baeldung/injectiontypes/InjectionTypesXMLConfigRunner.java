/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.baeldung.injectiontypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.baeldung.injectiontypes.domain.Student;
import com.baeldung.injectiontypes.xmlconfig.service.StudentService;

public class InjectionTypesXMLConfigRunner {

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        StudentService serviceInjectByConstructor = (StudentService) context
        		.getBean("serviceInjectDependencyByConstrucot");
        StudentService serviceInjectBySetter = (StudentService) context
        		.getBean("serviceInjectDependencyBySetter");
        
        Student student = new Student("Alex", 25);
        Student student2 = new Student("David", 26);
        
        serviceInjectByConstructor.addStudent(student);
        serviceInjectBySetter.addStudent(student2);

    }

}
