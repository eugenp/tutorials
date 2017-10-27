package com.baeldung.configuration;

import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import com.baeldung.dependency.Student;

@Configuration
@ComponentScan("com.baeldung.dependency")
public class ApplicationContextTestBeanInjection {

    @Bean
    public Student student() {
        return new Student("male", new ArrayList<String>());
    }

}
