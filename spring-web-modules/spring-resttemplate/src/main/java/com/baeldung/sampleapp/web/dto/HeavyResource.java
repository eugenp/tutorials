package com.baeldung.sampleapp.web.dto;


public class HeavyResource {
    private Integer id;
    private String name;
    private String surname;
    private Integer age;
    private String address;


    public HeavyResource() {
    }

    public HeavyResource(Integer id, String name, String surname, Integer age, String address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
