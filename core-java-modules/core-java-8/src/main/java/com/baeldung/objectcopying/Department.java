package com.baeldung.objectcopying;

public class Department {
    private String code;
    private String name;

    public Department(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Department(Department department) {
        this.code = department.getCode();
        this.name = department.getName();
    }    

    public String getCode() {
        return code;
    }    

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }    

    public void setName(String name) {
        this.name = name;
    }    
}