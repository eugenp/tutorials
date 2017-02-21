package com.baeldung.evaluation;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String args[]) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("evaluation.xml");

        Park app = (Park) context.getBean("park");
        app.searchMap();
    }
}
