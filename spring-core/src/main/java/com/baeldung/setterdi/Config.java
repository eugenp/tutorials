package com.baeldung.setterdi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.dependencyinjectiontypes.Student;
import com.baeldung.dependencyinjectiontypes.Student2;
import com.baeldung.dependencyinjectiontypes.TeacherFinder;
import com.baeldung.setterdi.domain.Engine;
import com.baeldung.setterdi.domain.Trailer;
import com.baeldung.setterdi.domain.Transmission;

@Configuration
@ComponentScan("com.baeldung.setterdi")
public class Config {

    @Bean
    public Engine engine() {
        Engine engine = new Engine();
        engine.setType("v8");
        engine.setVolume(5);
        return engine;
    }

    @Bean
    public Transmission transmission() {
        Transmission transmission = new Transmission();
        transmission.setType("sliding");
        return transmission;
    }

    @Bean
    public Trailer trailer() {
        Trailer trailer = new Trailer();
        return trailer;
    }
    
    @Bean 
    public TeacherFinder teacherFinder(){
    	TeacherFinder teacherFinder =new TeacherFinder();
    	teacherFinder.setTeacherFinder("author");
    	return teacherFinder;
    }
    
    @Bean
    public Student student() {
        return new Student(teacherFinder());
    }
 
    @Bean
    public Student2 student2() {
    	Student2 student2 = new Student2();
    	student2.setTeacherFinder(teacherFinder());
        return student2;
    }
 
}