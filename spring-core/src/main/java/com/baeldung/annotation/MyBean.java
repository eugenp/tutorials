package com.baeldung.annotation;

import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class MyBean {

    private final TestBean testBean;

    public MyBean(TestBean testBean) {
        this.testBean = testBean;
        System.out.println("Hello from constructor");
    }

    @PostConstruct
    private void postConstruct() {
        System.out.println("Hello from @PostConstruct method");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("Bean is being destroyed");
    }
}