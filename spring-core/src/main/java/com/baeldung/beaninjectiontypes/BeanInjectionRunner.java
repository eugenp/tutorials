package com.baeldung.beaninjectiontypes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.beaninjectiontypes.domian.Computer;

public class BeanInjectionRunner {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        Computer computer = context.getBean(Computer.class);

        System.out.println(computer);

    }

}
