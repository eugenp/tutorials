package com.baeldung.spring_groovy_config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;

public class MainApp {
    public static void main(String[] args){
        ApplicationContext context = new GenericGroovyApplicationContext("file:config/groovyTestWithRefBean.groovy");
        ClassWithRef test = (ClassWithRef) context.getBean("classWithRef");
    }
}
