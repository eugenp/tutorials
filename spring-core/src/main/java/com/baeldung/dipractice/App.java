package com.baeldung.dipractice;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Suyambu
 *
 */
@Configuration
@ComponentScan("com.baeldung.dipractice")
public class App {
    
    @Bean
    public Person person(){
        return new Person(20, "Tester");
    }
    
    public static void main(String[] args) {
        System.out.println("Hello World!");
        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        Processor processor = context.getBean("processor",Processor.class);
        processor.process();

    }
}
