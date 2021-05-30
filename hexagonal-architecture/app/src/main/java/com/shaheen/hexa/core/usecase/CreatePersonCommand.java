package com.shaheen.hexa.core.usecase;




public class CreatePersonCommand {
    String name;
    String firstName;
    Integer age;

    public CreatePersonCommand(String name, String firstName, Integer age) {
        this.name = name;
        this.firstName = firstName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public Integer getAge() {
        return age;
    }



    public String getName() {
        return name;
    }
}