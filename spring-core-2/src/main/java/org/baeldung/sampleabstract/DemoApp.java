package org.baeldung.sampleabstract;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.baeldung.sampleabstract")
public class DemoApp {


    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DemoApp.class);
    }

}
