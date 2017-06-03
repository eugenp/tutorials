package com.baeldung.springboot.rest.client;

import java.io.Serializable;

public class Greeting implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer id = null;
    private String content = null;
    
    /** Default constructor is mandatory for client */
    public Greeting() {
        super();
    }

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
    
    @Override
    public String toString() {
      return "Id: " + getId().toString() + " Content: " + getContent();
    }
}