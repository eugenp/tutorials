package com.baeldung.deepandshallowcopy;

import java.util.ArrayList;
import java.util.List;

public class DeepCopyExample {
    private String name;
    private int age;
    private List<Hobby> hobbies;

    public DeepCopyExample copy() {
        DeepCopyExample newCopy = new DeepCopyExample();
        newCopy.name = this.name;
        newCopy.age = this.age;
        newCopy.hobbies = new ArrayList<>();
        for (Hobby hobby : this.hobbies) {
            newCopy.hobbies.add(hobby.copy());
        }
        return newCopy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public static class Hobby {
        private String name;

        public Hobby(String name) {
            this.name = name;
        }

        public Hobby copy() {
            return new Hobby(this.name);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

