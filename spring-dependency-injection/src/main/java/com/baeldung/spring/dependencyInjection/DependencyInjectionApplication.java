package com.baeldung.spring.dependencyInjection;

import com.baeldung.spring.dependencyInjection.beans.MrBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class DependencyInjectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(DependencyInjectionApplication.class, args);
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        MrBean bean = (MrBean) context.getBean("mrBean");
        bean.sayHello();
    }

}
