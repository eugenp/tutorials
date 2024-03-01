package com.baeldung.deepvsshallow;

public class MarketingDepartment implements Cloneable {

  private String name;

  private Employee employee;

  public MarketingDepartment(String name) {
    this.name = name;
  }
  // standard getters and setters

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public MarketingDepartment clone() throws CloneNotSupportedException {
    MarketingDepartment marketingDepartmentClone = (MarketingDepartment) super.clone();
    marketingDepartmentClone.employee = employee.clone();
    return marketingDepartmentClone;
  }

  @Override
  public String toString() {
    return "MarketingDepartment{" + "name='" + name + '\'' + '}';
  }
}
