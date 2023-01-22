package com.baeldung.deepvsshallowcopy.shallow;


import com.baeldung.deepvsshallowcopy.Department;

public class User implements Cloneable {

    public String name;
    public Department department;

    public User(String name, Department department) {
        this.name = name;
        this.department = department;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
