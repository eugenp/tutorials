package com.baeldung.spring.dependencyInjection.beans;

public class DependencyBean {

    private Content content;

    public DependencyBean() {
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Content getContent() {
        return content;
    }
}
