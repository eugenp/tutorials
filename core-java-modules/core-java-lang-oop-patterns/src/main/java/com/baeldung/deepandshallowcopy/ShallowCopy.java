package com.baeldung.deepandshallowcopy;

import java.util.List;

public class ShallowCopy {
    private String name;
    private int age;
    private List<String> hobbies;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }
}
