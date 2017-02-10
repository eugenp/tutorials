/**
 * "Unpublished Work © 2017 Deere & Company. All Worldwide Rights Reserved. THIS MATERIAL IS THE PROPERTY OF DEERE &
 * COMPANY. ALL USE, ALTERATIONS, DISCLOSURE, DISSEMINATION AND/OR REPRODUCTION NOT SPECIFICALLY AUTHORIZED BY DEERE &
 * COMPANY IS PROHIBITED."
 */
package com.baeldung.lambda.dynamodb.bean;

import com.google.gson.Gson;

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
        final Gson gson = new Gson();
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
