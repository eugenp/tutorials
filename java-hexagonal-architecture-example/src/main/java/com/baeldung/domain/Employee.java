package com.baeldung.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize (builder = Employee.Builder.class)
public class Employee {

  private final String employeeId;
  private final String name;

  private Employee(
      Builder builder) {
    this.employeeId = builder.employeeId;
    this.name = builder.name;
  }

  public String getEmployeeId() {
    return employeeId;
  }

  public String getName() {
    return name;
  }

  public static Builder builder() {
    return new Builder();
  }

  @JsonPOJOBuilder
  public static final class Builder {
    private String employeeId;
    private String name;

    private Builder() {
    }

    public Builder withEmployeeId(String employeeId) {
      this.employeeId = employeeId;
      return this;
    }

    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Employee build() {
      return new Employee(this);
    }
  }
}
