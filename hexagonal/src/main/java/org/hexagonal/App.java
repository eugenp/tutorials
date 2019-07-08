package org.hexagonal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.hexagonal")
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext app = SpringApplication.run(App.class, args);
    }
}
