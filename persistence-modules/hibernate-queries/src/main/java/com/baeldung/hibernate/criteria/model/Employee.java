package com.baeldung.hibernate.criteria.model;

import java.io.Serializable;
import javax.persistence.Entity;

@org.hibernate.annotations.NamedQueries({ @org.hibernate.annotations.NamedQuery(name = "Employee_findByEmployeeId", query = "from Employee where id = :employeeId"),
  @org.hibernate.annotations.NamedQuery(name = "Employee_findAllByEmployeeSalary", query = "from Employee where salary = :employeeSalary")})
@org.hibernate.annotations.NamedNativeQueries({ @org.hibernate.annotations.NamedNativeQuery(name = "Employee_FindByEmployeeId", query = "select * from employee emp where employeeId=:employeeId", resultClass = Employee.class)})
@Entity
public class Employee implements Serializable {

  private static final long serialVersionUID = 1L;
  private Integer id;
  private String name;
  private Long salary;

  // constructors
  public Employee() {
  }

  public Employee(final Integer id, final String name, final Long salary) {
    super();
    this.id = id;
    this.name = name;
    this.salary = salary;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
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
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
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

  public Long getSalary() {
    return salary;
  }

  public void setSalary(Long salary) {
    this.salary = salary;
  }
}
