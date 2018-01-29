package com.baeldung.beaninjection.model;

/**
 * Created by ggallo on 23/01/2018.
 */
public class Author {

    private String name;
    private Integer age;

    public Author(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
