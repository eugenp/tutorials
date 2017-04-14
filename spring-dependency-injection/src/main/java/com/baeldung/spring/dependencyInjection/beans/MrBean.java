package com.baeldung.spring.dependencyInjection.beans;

public class MrBean {

    private DependencyBean dependency;

    public MrBean(DependencyBean dependencyBean) {
        this.dependency = dependencyBean;
    }

    public void sayHello() {
        System.out.println(dependency
          .getContent()
          .sayHello());
    }

}
