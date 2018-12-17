package com.baeldung.channel.rest;

public class CreateEmployeeResponse {

    private Integer id;
    private String employeename;

    public CreateEmployeeResponse() {
    }

    public CreateEmployeeResponse(Integer id, String employeename) {
        this.id = id;
        this.employeename = employeename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }
}