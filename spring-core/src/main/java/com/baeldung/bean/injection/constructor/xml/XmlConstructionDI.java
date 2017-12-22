package com.baeldung.bean.injection.constructor.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlConstructionDI {

    public static void main(String[] args) {

        final ApplicationContext context = new ClassPathXmlApplicationContext("bean-injection/constructor-bean-injection.xml");

        User user = context.getBean("user", User.class);

        System.out.println(user.toString());

    }

}
