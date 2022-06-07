package com.baeldung.find

class Person {
    private String firstname
    private String lastname
    private Integer age

    Person(String firstname, String lastname, Integer age) {
        this.firstname = firstname
        this.lastname = lastname
        this.age = age
    }

    String getFirstname() {
        return firstname
    }

    void setFirstname(String firstname) {
        this.firstname = firstname
    }

    String getLastname() {
        return lastname
    }

    void setLastname(String lastname) {
        this.lastname = lastname
    }

    Integer getAge() {
        return age
    }

    void setAge(Integer age) {
        this.age = age
    }
}
