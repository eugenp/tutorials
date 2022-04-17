package com.baeldung.java.shallow.deep.copy;

public class PersonShallowCopy implements Cloneable {
    private StringBuilder name;
    private int age;

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public StringBuilder getName() {
        return name;
    }

    public void setName(final StringBuilder name) {
        this.name = name;
    }
}
