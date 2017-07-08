package com.baeldung.application;

import com.baeldung.services.LowercaseTextService;
import com.baeldung.services.TextPrinter;
import com.baeldung.services.TextService;
import com.baeldung.services.UppercaseTextService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class Application {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        TextPrinter textPrinter = context.getBean(TextPrinter.class);
        textPrinter.printText("Hello from Spring");
    }

    @Bean
    public TextService lowercaseTextService() {
        return new LowercaseTextService();
    }

    @Bean
    public TextService uppercaseTextService() {
        return new UppercaseTextService();
    }

    @Bean
    public TextPrinter textPrinter() {
        return new TextPrinter(lowercaseTextService());
    }
}
