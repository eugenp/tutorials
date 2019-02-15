package com.baeldung.easyrules.domain;

public class Person {
    private String name;
    private int age;
    private boolean adult;
    
    public Person (String name, int age) {
	this.name = name;
	this.age = age;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }
    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }
    /**
     * @return the adult
     */
    public boolean isAdult() {
        return adult;
    }
    /**
     * @param adult the adult to set
     */
    public void setAdult(boolean adult) {
        this.adult = adult;
    }
    
    
}
