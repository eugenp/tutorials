package com.baeldung.copies;

import java.util.List;

public class PersonShallow {
    private String name;
    private int age;
    private List<String> hobbies;

    public PersonShallow(String name, int age, List<String> hobbies) {
        this.name = name;
        this.age = age;
        this.hobbies = hobbies;
    }

    public PersonShallow shallowCopy() { return new PersonShallow(this.name, this.age, this.hobbies); }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

}
