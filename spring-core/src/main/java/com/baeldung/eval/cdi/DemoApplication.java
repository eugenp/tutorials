package com.baeldung.eval.cdi;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = { "com.baeldung.eval.cdi" })
public class DemoApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context 
            = new AnnotationConfigApplicationContext(DemoApplication.class);

        Author author = (Author) context.getBean("author");
        author.startDraft();
    }

    @Bean
    public Computer getComputer() {
        return new Laptop();
    }
}
