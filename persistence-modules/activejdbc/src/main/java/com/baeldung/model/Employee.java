package com.baeldung.model;


import org.javalite.activejdbc.Model;

public class Employee extends Model {

    public Employee(){

    }

    public Employee(String firstName, String lastName, String gender, String createdBy) {
        set("first_name1",firstName);
        set("last_name",lastName);
        set("gender",gender);
        set("created_by",createdBy);
    }

    public String getLastName() {
        return getString("last_name");
    }

}
