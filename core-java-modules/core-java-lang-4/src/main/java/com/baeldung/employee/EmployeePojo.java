package com.baeldung.employee;

import java.time.LocalDate;
import java.util.Objects;

public class EmployeePojo {

  public String firstName;
  public String lastName;
  private LocalDate startDate;

  public EmployeePojo(String firstName, String lastName, LocalDate startDate) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.startDate = startDate;
  }

  //Getters and Setters

  @Override
  public boolean equals(Object obj) {
    return Objects.equals(firstName, this.firstName)
        && Objects.equals(lastName, this.lastName)
        && Objects.equals(startDate, this.startDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, startDate);
  }

}