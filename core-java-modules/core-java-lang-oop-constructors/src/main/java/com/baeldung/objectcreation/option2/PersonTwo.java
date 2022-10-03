package com.baeldung.objectcreation.option2;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PersonTwo {
    private String name;
    private int uid;

    public PersonTwo() {
        this.name = "Maya Kumari";
        this.uid = 101;
    }

    public PersonTwo(String name) {
        this.name = name;
        this.uid = 102;
    }

    public PersonTwo(String name, Integer uid) {
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
        try {
            Class.forName("com.baeldung.option2.PersonTwo");

            Constructor c1 = PersonTwo.class.getConstructor();
            PersonTwo p1 = (PersonTwo) c1.newInstance();
            System.out.println("Name: " + p1.getName());
            System.out.println("UID: " + p1.getUid());

            Constructor c2 = PersonTwo.class.getConstructor(String.class);
            PersonTwo p2 = (PersonTwo) c2.newInstance("James Gunn");
            System.out.println("Name: " + p2.getName());
            System.out.println("UID: " + p2.getUid());

            Constructor c3 = PersonTwo.class.getConstructor(String.class, Integer.class);
            PersonTwo p3 = (PersonTwo) c3.newInstance("Mark Brown", 103);
            System.out.println("Name: " + p3.getName());
            System.out.println("UID: " + p3.getUid());

        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
