package com.baeldung.hexagonalarch.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.context.annotation.Primary;

@Entity
@Table(name = "student")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long studentId;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  public Student(Long studentId, String firstName, String lastName) {
    this.studentId = studentId;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Student() {

  }

  public Long getStudentId() {
    return studentId;
  }

  public void setStudentId(Long studentId) {
    this.studentId = studentId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

}
