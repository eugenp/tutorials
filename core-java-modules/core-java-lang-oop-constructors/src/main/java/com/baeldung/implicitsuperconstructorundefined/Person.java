package com.baeldung.implicitsuperconstructorundefined;

public class Person {

    private String name;
    
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
    
    // comment this constructor to see the error.
    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    
}
