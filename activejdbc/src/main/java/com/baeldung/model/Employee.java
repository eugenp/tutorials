package com.baeldung.model;


import org.javalite.activejdbc.Model;

public class Employee extends Model {

    public Employee(){

    }

    public Employee(String firstName, String lastName, String gender, String createdBy) {
        set("first_name",firstName);
        set("last_name",lastName);
        set("gender",gender);
        set("created_by",createdBy);
    }

    public void setUpdatedBy(String updatedBy){
        set("updated_by",updatedBy);
    }

    public String getLastName(){
        return getString("last_name");
    }

    public String getFirstName(){
        return getString("first_name");
    }

}
