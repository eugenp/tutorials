package com.rabbitmq.lihongjie.demo.model;

import java.io.Serializable;

/**
 * Created by lihongjie on 11/19/17.
 */
public class Account implements Serializable {

    private Integer id;

    private String name;

    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
