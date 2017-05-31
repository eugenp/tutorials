package com.baeldung.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by elyor on 27.05.2017.
 */
public class MainApp {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

        SimpleDirectorySynchronizer synchronizer1 = (SimpleDirectorySynchronizer) context.getBean("synchronizer1");
        synchronizer1.run();

        SimpleDirectorySynchronizer synchronizer2 = (SimpleDirectorySynchronizer) context.getBean("synchronizer2");
        synchronizer2.run();
    }
}
