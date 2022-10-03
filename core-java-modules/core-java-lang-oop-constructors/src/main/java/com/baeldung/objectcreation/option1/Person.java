package com.baeldung.objectcreation.option1;

public class Person {
    private String name;
    private int uid;

    public Person() {
        this.name = "Michael Cole";
        this.uid = 101;
    }

    public Person(String name, int uid) {
        super();
        this.name = name;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public static void main(String[] args) {

        Person p1 = new Person();
        Person p2 = new Person("John Bodgan", 102);
        System.out.println("Name: " + p1.getName() + " UID: " + p1.getUid());
        System.out.println("Name: " + p2.getName() + " UID: " + p2.getUid());
    }

}
