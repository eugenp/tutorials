package com.baeldung.beaninjectiontypes.xmlconfigexample;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanInjectorXMLExample {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("bean-injector.xml");
        SimpleBeanConstructorInjection simpleBeanConstructorInjection = (SimpleBeanConstructorInjection) ctx.getBean("simpleBeanConstructorInjection");
        simpleBeanConstructorInjection.doSomething();

        System.out.println("********************");
        SimpleBeanSetterInjection simpleBeanSetterInjection = (SimpleBeanSetterInjection) ctx.getBean("simpleBeanSetterInjection");
        simpleBeanSetterInjection.doSomething();
    }
}
