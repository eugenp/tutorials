package com.baeldung.lazyinitialization;

import com.baeldung.lazyinitialization.services.Writer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Bean("writer1")
    public Writer getWriter1() {
        return new Writer("Writer 1");
    }

    @Bean("writer2")
    public Writer getWriter2() {
        return new Writer("Writer 2");
    }

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Application context initialized!!!");

        Writer writer1 = ctx.getBean("writer1", Writer.class);
        writer1.write("First message");

        Writer writer2 = ctx.getBean("writer2", Writer.class);
        writer2.write("Second message");
    }

    /*
    This method shows how to set lazy initialization and start the application using SpringApplicationBuilder
     */
    private static ApplicationContext runUsingSpringApplicationBuilder(String[] args){
        return new SpringApplicationBuilder(Application.class)
                .lazyInitialization(true)
                .build(args)
                .run();
    }

    /*
    This method shows how to set lazy initialization and start the application using SpringApplication
     */
    private static ApplicationContext runUsingSpringApplication(String[] args){
        SpringApplication app = new SpringApplication(Application.class);
        app.setLazyInitialization(true);
        return app.run(args);
    }
}
