package com.baeldung.option3;

public class Person implements Cloneable {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private String name;
    private int uid;

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
        Person p1 = new Person("Ryan", 101);
        try {
            Person p2 = (Person) p1.clone();
            System.out.println("Name: " + p2.getName());
            System.out.println("UID: " + p2.getUid());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }

}
