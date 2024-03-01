package com.baeldung.deepvsshallow;

public class Employee implements Cloneable {

  private String name;

  public Employee(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Employee clone() throws CloneNotSupportedException {
    return (Employee) super.clone();
  }

  @Override
  public String toString() {
    return "Employee{" + "name='" + name + '\'' + '}';
  }
}
