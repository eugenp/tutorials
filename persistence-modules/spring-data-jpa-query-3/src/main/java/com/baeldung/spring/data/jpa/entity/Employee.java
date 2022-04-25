package com.baeldung.spring.data.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Employee {

  @Id
  @GeneratedValue
  private Integer employeeId;

  private String employeeName;

  private Long employeeSalary;

}
