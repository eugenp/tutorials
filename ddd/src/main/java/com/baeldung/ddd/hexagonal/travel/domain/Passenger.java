package com.baeldung.ddd.hexagonal.travel.domain;

public class Passenger {

    private String name;
    private String ssn;
    private Integer age;

    public Passenger(String name, String ssn, Integer age) {
        this.name = name;
        this.ssn = ssn;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
