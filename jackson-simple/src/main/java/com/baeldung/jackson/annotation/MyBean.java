package com.baeldung.jackson.annotation;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({ "name", "id" })
public class MyBean {
    public int id;
    public String name;

    public MyBean() {

    }

    public MyBean(final int id, final String name) {
        this.id = id;
        this.name = name;
    }
    
    @JsonGetter("name")
    public String getTheName() {
        return name;
    }
    
    @JsonSetter("name")
    public void setTheName(String name) {
        this.name = name;
    }
}
