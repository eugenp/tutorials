package com.baeldung.di.types.config;

import com.baeldung.di.types.annotation.Cafeteria;
import com.baeldung.di.types.annotation.Security;
import com.baeldung.di.types.annotation.StudentService;
import com.baeldung.di.types.annotation.TeacherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.baeldung.di.types.annotation"})
public class DIConfig {

    @Bean
    public TeacherService teacherService() {
        return new TeacherService();
    }

    @Bean
    public StudentService studentService() {
        return new StudentService();
    }

    @Bean
    public Security security() {
        return new Security();
    }

    @Bean
    public Cafeteria cafeteria() {
        return new Cafeteria();
    }
}
