package models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersonSerializeable implements Serializable {

    private String name;
    private int age;
    private List<String> hobbies;

    public PersonSerializeable(String name, int age) {
        this.name = name;
        this.age = age;
        this.hobbies = new ArrayList<>();
    }

    public void addHobby(String hobby) {
        hobbies.add(hobby);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

