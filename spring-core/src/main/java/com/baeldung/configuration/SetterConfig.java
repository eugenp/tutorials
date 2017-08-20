package com.baeldung.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.model.School;
import com.baeldung.model.Student;

@Configuration
public class SetterConfig {

    @Bean
    public Student student() {
        Student student = new Student();
        student.setId(2);
        student.setName("Sam");
        student.setSchool(school());
        return student;
    }

    @Bean
    public School school() {
        School school = new School();
        school.setId(2);
        school.setName("BUET");
        return school;
    }
}
