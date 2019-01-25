package com.baeldung.initializationguide;

import java.io.Serializable;

/**
 * 代码块、静态代码块、构造方法初始化顺序
 */
public class User implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    static String forum;
    private String name;
    private int id;

    {
        id = 0;
        System.out.println("Instance Initializer");
    }

    static {
    	forum = "Java";
        System.out.println("Static Initializer");
    }

    public User(String name, int id) {
        super();
        this.name = name;
        this.id = id;
    }

    public User() {
        System.out.println("Constructor");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}

