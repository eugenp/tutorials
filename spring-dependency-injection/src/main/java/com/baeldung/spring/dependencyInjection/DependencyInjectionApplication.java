package com.baeldung.spring.dependencyInjection;

import com.baeldung.spring.dependencyInjection.beans.MrBean;
import com.baeldung.spring.dependencyInjection.config.BeanConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class DependencyInjectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(DependencyInjectionApplication.class, args);
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        MrBean bean = context.getBean(MrBean.class);
        bean.sayHello();
    }

}
