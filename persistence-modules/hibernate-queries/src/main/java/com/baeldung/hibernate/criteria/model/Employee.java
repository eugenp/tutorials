package com.baeldung.hibernate.criteria.model;

import java.io.Serializable;

public class Employee implements Serializable {

  private static final long serialVersionUID = 1L;
  private Integer employeeId;
  private String employeeName;
  private Long employeeSalary;

  // constructors
  public Employee() {
  }

  public Employee(final Integer employeeId, final String employeeName, final Long employeeSalary) {
    super();
    this.employeeId = employeeId;
    this.employeeName = employeeName;
    this.employeeSalary = employeeSalary;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final Employee other = (Employee) obj;
    if (employeeId == null) {
      if (other.employeeId != null)
        return false;
    } else if (!employeeId.equals(other.employeeId))
      return false;
    return true;
  }

  public Integer getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Integer employeeId) {
    this.employeeId = employeeId;
  }

  public String getEmployeeName() {
    return employeeName;
  }

  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }

  public Long getEmployeeSalary() {
    return employeeSalary;
  }

  public void setEmployeeSalary(Long employeeSalary) {
    this.employeeSalary = employeeSalary;
  }
}
