package com.baeldung.assertj.custom;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String fullName;
    private int age;
    private List<String> nicknames;

    public Person(String fullName, int age) {
        this.fullName = fullName;
        this.age = age;
        this.nicknames = new ArrayList<>();
    }

    public void addNickname(String nickname) {
        nicknames.add(nickname);
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public List<String> getNicknames() {
        return nicknames;
    }
}
