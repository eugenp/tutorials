package com.baeldung.lambda.dynamodb.bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PersonRequest {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String address;

    public static void main(String[] args) {
        PersonRequest personRequest = new PersonRequest();
        personRequest.setId(1);
        personRequest.setFirstName("John");
        personRequest.setLastName("Doe");
        personRequest.setAge(30);
        personRequest.setAddress("United States");
        System.out.println(personRequest);
    }

    public String toString() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    private void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    private void setAddress(String address) {
        this.address = address;
    }
}
