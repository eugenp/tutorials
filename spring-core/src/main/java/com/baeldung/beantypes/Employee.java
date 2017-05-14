/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.beantypes;

/**
 *
 * @author tehreem.nisa
 */
public class Employee {

    private long salary;
    private String title;

    public Employee() {
    }

    public Employee(long salary, String title) {
        this.salary = salary;
        this.title = title;
    }

    public void setsalary(long salary) {
        this.salary = salary;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public void show() {
        System.out.println("------------------" + "\n" + "Title:" + title + "\n" + "Salary:" + salary + "\n");
    }
}
