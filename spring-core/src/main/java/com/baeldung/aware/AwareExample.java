package com.baeldung.aware;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Gebruiker on 4/24/2018.
 */
public class AwareExample {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(Config.class);

        MyBeanName myBeanName = context.getBean(MyBeanName.class);

        MyBeanFactory myBeanFactory = context.getBean(MyBeanFactory.class);
        myBeanFactory.getMyBeanName();
    }
}
