package com.baeldung.clonable;

import java.util.ArrayList;
import java.util.List;

public class Human implements Cloneable {

    private String name;
    private List<String> hobbies;

    public Human(String name, List<String> hobbies) {
        this.name = name;
        this.hobbies = hobbies;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Human clonedHuman = (Human) super.clone();
        clonedHuman.hobbies = new ArrayList<>(this.hobbies);
        return clonedHuman;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getHobbies() {
        return hobbies;
    }
}
