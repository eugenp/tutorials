package com.baeldung.objectcopy.objects;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Person implements Cloneable {
    private String name;
    private int score;
    private List<String> hobbies;

    public Person(String name, int score, List<String> hobbies) {
        this.name = name;
        this.score = score;
        this.hobbies = hobbies;
    }

    public Person(Person other) {
        this.name = other.name;
        this.score = other.score;
        this.hobbies = new ArrayList<>(other.hobbies);
    }

    // standard getters and setters

    @Override
    public Person clone() {
        try {
            Person cloned = (Person) super.clone();

            cloned.hobbies = new ArrayList<>(hobbies);

            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
