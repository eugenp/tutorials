package org.apache.camel.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(final String[] args) throws Exception {
    	//Load application context
    	ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("camel-context.xml");
        Thread.sleep(5000);
        applicationContext.close();
    }
}