package org.baeldung.sampleabstract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan(basePackages = "org.baeldung.sampleabstract")
public class DemoApp {

    @Autowired
    private FooService fooService;

    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DemoApp.class);
    }

    @PostConstruct
    public void afterInitialize() {

        fooService.afterInitialize();
    }
}
