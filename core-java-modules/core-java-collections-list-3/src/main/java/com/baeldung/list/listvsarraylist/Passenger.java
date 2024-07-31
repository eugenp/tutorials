package com.baeldung.list.listvsarraylist;

public class Passenger {

    private String name;
    private int age;
    private String source;
    private String destination;

    public Passenger(String name, int age, String source, String destination) {
        this.name = name;
        this.age = age;
        this.source = source;
        this.destination = destination;
    }

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

}
