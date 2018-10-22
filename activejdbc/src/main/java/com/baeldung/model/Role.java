package com.baeldung.model;


import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

@Table("EMP_ROLES")
public class Role extends Model {

    public Role(){

    }

    public Role(String role,String createdBy){
        set("role_name",role);
        set("created_by",createdBy);
    }

    public String getRoleName() {
        return getString("role_name");
    }
}
