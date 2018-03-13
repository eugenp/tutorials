package com.spaceprogram.bigcache;

import java.io.Serializable;

/**
 * User: treeder
 * Date: Sep 22, 2008
 * Time: 2:45:49 PM
 */
public class SomeObject2 implements Serializable {

    private String name;

    public SomeObject2() {
    }

    public SomeObject2(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SomeObject2{" +
                "name='" + name + '\'' +
                '}';
    }
}
