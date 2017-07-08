package com.baeldung.application;

import com.baeldung.services.LowercaseTextService;
import com.baeldung.services.TextPrinter;
import com.baeldung.services.TextService;
import com.baeldung.services.UppercaseTextService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/*
@Configuration
@ComponentScan
*/
public class Application {

    public static void main(String[] args) {

        /*
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        TextPrinter textPrinter = context.getBean(TextPrinter.class);
        textPrinter.printText("Hello from Spring");
        */
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        BeanFactory factory = context;
        TextPrinter textPrinter = (TextPrinter) factory.getBean("TextPrinter");
        textPrinter.printText("Hello from Spring");
    }

    /*
    @Bean(name = "lowercaseTextService")
    public TextService lowercaseTextService() {
        return new LowercaseTextService();
    }

    @Bean(name = "uppercaseTextService")
    public TextService uppercaseTextService() {
        return new UppercaseTextService();
    }

    @Bean
    public TextPrinter textPrinter() {
        return new TextPrinter(lowercaseTextService());
    }
    */
}
