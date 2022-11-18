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
public class StudentCloneExampleObj implements Cloneable {

    public Name name;
    private final int age;

    public StudentCloneExampleObj(Name name, int age) {
        this.name = name;
        this.age = age;
    }

    public void printInfo() {
        System.out.println("Name= " + this.getName().getName() + ", Surname= " + this.getName().getSurname() + ", age= " + age);
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public StudentCloneExampleObj clone() {
        try {
            StudentCloneExampleObj cloned = (StudentCloneExampleObj) super.clone();
            cloned.setName((Name) cloned.getName().clone());
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();

        }
    }

}
