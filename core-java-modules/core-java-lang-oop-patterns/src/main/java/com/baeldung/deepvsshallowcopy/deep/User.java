package com.baeldung.deepvsshallowcopy.deep;

import com.baeldung.deepvsshallowcopy.Department;

public class User implements Cloneable {

    public String name;
    public Department department;

    public User(String name, Department department) {
        this.name = name;
        this.department = department;
    }

    @Override
    public Object clone() {
        return new User(this.name, new Department(this.department.id, this.department.zone));
    }
}
