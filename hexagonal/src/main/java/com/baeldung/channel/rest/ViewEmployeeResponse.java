package com.baeldung.channel.rest;
public class ViewEmployeeResponse {

    private Integer id;
    private String name;
    private String role;
    private long salary;
    

    public ViewEmployeeResponse() {
    }

    public ViewEmployeeResponse(Integer id, String name, String role, long salary) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.salary = salary;
     
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

   
}