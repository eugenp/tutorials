package com.baeldung.pattern.hexagonal.dto;

public class EmployeeDto {

    Integer id;

    String name;

    Integer age;

    String department;



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



    public Integer getAge() {
        return age;
    }



    public void setAge(Integer age) {
        this.age = age;
    }



    public String getDepartment() {
        return department;
    }



    public void setDepartment(String department) {
        this.department = department;
    }



    @Override
    public String toString() {
        return "EmployeeDto [id=" + id + ", name=" + name + ", age=" + age + ", department=" + department + "]";
    }
    
    
}
