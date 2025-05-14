package com.baeldung.copyproperties;

public class TempDTO {
    public String name;
    public int age;

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

    public TempDTO(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public TempDTO() {
    }
}
