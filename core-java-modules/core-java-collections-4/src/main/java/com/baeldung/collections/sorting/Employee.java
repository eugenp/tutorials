package com.baeldung.collections.sorting;

import java.util.Date;

public class Employee implements Comparable {

  private String name;
  private int age;
  private double salary;
  private Date employeeJoiningDate;

  public Employee(String name, int age, double salary, Date employeeJoiningDate) {
    this.name = name;
    this.age = age;
    this.salary = salary;
    this.employeeJoiningDate = employeeJoiningDate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public double getSalary() {
    return salary;
  }

  public void setSalary(double salary) {
    this.salary = salary;
  }

  public Date getEmployeeJoiningDate() {
    return employeeJoiningDate;
  }

  public void setEmployeeJoiningDate(Date employeeJoiningDate) {
    this.employeeJoiningDate = employeeJoiningDate;
  }

  @Override
  public String toString() {
    return new StringBuffer().append("(")
      .append(getName())
      .append(getAge())
      .append(",")
      .append(getSalary())
      .append(")")
      .toString();
  }

}
