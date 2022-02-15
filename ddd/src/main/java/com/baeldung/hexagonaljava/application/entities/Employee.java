package com.baeldung.hexagonaljava.application.entities;

import java.io.Serializable;

/**
 * @author Hesam Ghiasi created on 2/15/22
 */
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "{" + "name=" + name + ", age=" + age + '}';
    }
}
