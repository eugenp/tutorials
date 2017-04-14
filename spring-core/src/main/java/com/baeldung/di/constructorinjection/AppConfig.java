package com.baeldung.di.constructorinjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Application configuration class.
 * We define <{@link Department} and {@link PersonalDetails} beans in this config file
 *
 */
@Configuration
@ComponentScan("com.baeldung.di.constructorinjection")
public class AppConfig {

    /**
     * Method to create Department instance
     * @see Department
     * @return instance of Department
     */
    @Bean
    public Department getDepartment() {
        Department dept = new Department("Chemistry");
        return dept;
    }

    /**
     * Method to create PersonalDetails instance.
     * @see PersonalDetails
     * @return instance of PersonalDetails
     */
    @Bean
    public PersonalDetails getPersonalInfo() {
        PersonalDetails kimPersonalDetails = new PersonalDetails("Kim", "Chem001", 18);
        return kimPersonalDetails;
    }

}
