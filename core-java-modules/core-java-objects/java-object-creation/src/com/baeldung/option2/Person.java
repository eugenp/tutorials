package com.baeldung.option2;

public class Person {
    private String name;
    private int uid;

    public Person() {
        this.name = "Carl Max";
        this.uid = 101;
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

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class c = Class.forName("com.baeldung.option2.Person");
        @SuppressWarnings("deprecation")
        Person p = (Person) c.newInstance();
        System.out.println("Name: " + p.getName());
        System.out.println("UID: " + p.getUid());
    }

}
