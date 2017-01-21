package com.java.test;
import  java.io.Serializable;
/**
 * Created by s738204 on 11/01/2017.
 */
public class Emp implements Serializable {
    public String name;
    public int id;

    public Emp(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        return "Employee [ Employee Name: "+name+", Employee Id: "+ id+ " ]";
    }
}