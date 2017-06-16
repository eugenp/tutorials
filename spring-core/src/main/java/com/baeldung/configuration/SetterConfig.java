package com.baeldung.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.model.College;
import com.baeldung.model.Student;

@Configuration
public class SetterConfig {

    @Bean
    public Student student() {
        Student student = new Student();
        student.setId(2);
        student.setName("Tom");
        student.setCollege(college());
        return student;
    }

    @Bean
    public College college() {
        College college = new College();
        college.setId(2);
        college.setName("Pittsburgh");
        return college;
    }
}
