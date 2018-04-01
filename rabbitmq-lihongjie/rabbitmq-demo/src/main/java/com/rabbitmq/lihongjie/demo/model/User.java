package com.rabbitmq.lihongjie.demo.model;

import java.io.Serializable;

/**
 * Created by lihongjie on 11/19/17.
 */
public class User implements Serializable {

    private Integer id;

    private String name;

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
}
