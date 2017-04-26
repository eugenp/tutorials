package com.baeldung.spring_groovy_config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;

public class MainApp {
    ApplicationContext context = new GenericGroovyApplicationContext("file:config/groovyPropConfig.groovy");
    TestClassB test = (TestClassB) context.getBean("testClassB"); //see the test class AppTest.java for more examples
}
