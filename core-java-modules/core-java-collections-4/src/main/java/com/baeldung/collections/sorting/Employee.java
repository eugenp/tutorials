package com.baeldung.collections.sorting;

import java.util.Date;

public class Employee implements Comparable<Employee>{

  private String name;
  private int age;
  private double salary;
  private Date joiningDate;

  public Employee(String name, int age, double salary, Date joiningDate) {
    this.name = name;
    this.age = age;
    this.salary = salary;
    this.joiningDate = joiningDate;
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

  public Date getJoiningDate() {
    return joiningDate;
  }

  public void setJoiningDate(Date joiningDate) {
    this.joiningDate = joiningDate;
  }

  @Override
  public boolean equals(Object obj) {
    return ((Employee) obj).getName()
      .equals(getName());
  }

  @Override
  public String toString() {
    return new StringBuffer().append("(")
      .append(getName()).append(",")
      .append(getAge())
      .append(",")
      .append(getSalary()).append(",").append(getJoiningDate())
      .append(")")
      .toString();
  }

  @Override
  public int compareTo(Employee employee) {
    return getJoiningDate().compareTo(employee.getJoiningDate());
  }
}
