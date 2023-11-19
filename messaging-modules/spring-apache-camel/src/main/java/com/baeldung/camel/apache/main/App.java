package com.baeldung.camel.apache.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(final String[] args) throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("camel-context.xml");
        // Keep main thread alive for some time to let application finish processing the input files.
        Thread.sleep(5000);
        applicationContext.close();
    }
}