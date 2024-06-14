package com.baeldung.deepandshallowcopy;

import java.util.ArrayList;
import java.util.List;

public class DeepCopy {
    private String name;
    private int age;
    public List<String> hobbies;

    public DeepCopy copy() {
        DeepCopy newCopy = new DeepCopy();
        newCopy.name = this.name;
        newCopy.age = this.age;
        newCopy.hobbies = new ArrayList<>(this.hobbies);
        return newCopy;
    }

    public <E> List getHobbies() {
        return null;
    }

    public void setHobbies(List<String> hobbies) {

    }
}
