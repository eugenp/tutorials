package com.baeldung.hexagonal.config;

import com.baeldung.hexagonal.adapter.FileDataAdapter;
import com.baeldung.hexagonal.adapter.StudentAdaptar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${some.jsonfile:students.json}")
    private String fileName;

    @Bean
    public FileDataAdapter getDataAdaptar() {
        return new FileDataAdapter(fileName);
    }

    @Bean
    public StudentAdaptar getStudentAdaptar() {
        return new StudentAdaptar();
    }

}
