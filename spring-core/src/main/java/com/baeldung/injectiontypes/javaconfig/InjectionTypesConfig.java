package com.baeldung.injectiontypes.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.injectiontypes.dao.StudentDAO;


@Configuration
@ComponentScan(value={"com.baeldung.injectiontypes.javaconfig.service"})
public class InjectionTypesConfig {

    @Bean
    public StudentDAO getStudentDAO(){
        return new StudentDAO();
    }
    
}
