package com.baeldung.springboot.rest;

public class Greeting {
    private static final long serialVersionUID = 1L;
    
    private Integer id = null;
    private String content = null;

    public Greeting(Integer id) {
        this.id = id;
        this.content = "Hello World";
    }

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}