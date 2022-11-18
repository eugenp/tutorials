/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.deepvsshallow;

/**
 *
 * @author mantas.pipine
 */
public class StudentConstructorExampleObj {

    public Name name;
    private int age;

    public StudentConstructorExampleObj(Name name, int age) {
        this.name = name;
        this.age = age;
    }

    public StudentConstructorExampleObj(StudentConstructorExampleObj s) {
        this.age = s.getAge();
        this.name = s.getName(); // Shallow copy
        // this.name = new Name(s.getName().getName(),s.getName().getSurname()); // Deep copy
        //this.setName(new Name(s.getName().getName(), s.getName().getSurname()));
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String printInfo() {
        String ats = ("Name= " + this.getName().getName() + ", Surname= " + this.getName().getSurname() + ", age= " + this.getAge());
        System.out.println(ats);
        return ats;
    }

}
