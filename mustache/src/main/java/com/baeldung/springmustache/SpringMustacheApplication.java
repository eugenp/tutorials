package com.baeldung.springmustache;

import com.samskivert.mustache.DefaultCollector;
import com.samskivert.mustache.Mustache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.baeldung"})
public class SpringMustacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMustacheApplication.class, args);
    }

    @Bean
    public Mustache.Compiler mustacheCompiler(Mustache.TemplateLoader templateLoader) {
        return Mustache.compiler()
          .defaultValue("Some Default Value")
          .withLoader(templateLoader)
          .withCollector(new DefaultCollector());
    }
}

