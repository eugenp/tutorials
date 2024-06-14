package com.baeldung.deepandshallowcopy;

import java.util.ArrayList;
import java.util.List;

public class Copy {

    private String name;
    private List<String> hobbies;

    public Copy(String name, List<String> hobbies) {
        this.name = name;
        this.hobbies = hobbies;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Copy deepCopy() {
        List<String> newHobbies = new ArrayList<>(this.hobbies);
        return new Copy(this.name, newHobbies);
    }

}
