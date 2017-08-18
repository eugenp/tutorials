package com.baeldung.configuration;
 
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 
 import com.baeldung.model.School;
 import com.baeldung.model.Student;
 
 @Configuration
 public class ConstructorConfig {
 
     @Bean
     public Student student() {
         return new Student(1, "Peter", school());
     }
 
     @Bean
     public School school() {
         return new School(1, "Massachusetts Institute of Technology");
     }
 }
