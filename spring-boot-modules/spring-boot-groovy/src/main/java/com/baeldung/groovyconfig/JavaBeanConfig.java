package com.baeldung.groovyconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaBeanConfig {

    @Bean
    public JavaPersonBean javaPerson() {
        JavaPersonBean jPerson = new JavaPersonBean();
        jPerson.setFirstName("John");
        jPerson.setLastName("Doe");
        jPerson.setAge("31");
        jPerson.setEyesColor("green");
        jPerson.setHairColor("blond");
        
        return jPerson;
    }

}
