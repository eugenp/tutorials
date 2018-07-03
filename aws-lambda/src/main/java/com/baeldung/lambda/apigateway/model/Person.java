package com.baeldung.lambda.apigateway.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Person {

    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String address;

    public Person(String json) {
        Gson gson = new Gson();
        Person request = gson.fromJson(json, Person.class);
        this.id = request.getId();
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.age = request.getAge();
        this.address = request.getAddress();
    }

    public String toString() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
	}
}
