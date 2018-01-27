package com.baeldung.beaninjectiontypes.autowiredexample;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class BeanInjectorAutowiredExample {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BeanInjectorAutowiredExample.class);
        SimpleAutowiredBean simpleBean = (SimpleAutowiredBean) ctx.getBean("simpleAutowiredBean");
        simpleBean.doSomething();
    }

}
