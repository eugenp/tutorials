/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.hexagon.model;

import java.io.Serializable;

/**
 *
 * @author NandomPC
 */
public class Student implements Serializable {

    private Integer Id;
    private String name;
    private String email;

    public Student() {
    }

    public Student(Integer Id, String name, String email) {
        this.Id = Id;
        this.name = name;
        this.email = email;
    }
    
    //getters and setters
    
    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
