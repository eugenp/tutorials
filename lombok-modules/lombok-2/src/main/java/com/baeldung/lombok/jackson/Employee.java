package com.baeldung.lombok.jackson;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "EmployeeBuilder")
@JsonDeserialize(builder = Employee.EmployeeBuilder.class)
@AllArgsConstructor
public class Employee {

    private int identity;
    private String firstName;

    @JsonPOJOBuilder(buildMethodName = "createEmployee", withPrefix = "construct")
    public static class EmployeeBuilder {

        private int idValue;
        private String nameValue;

        public EmployeeBuilder constructId(int id) {
            idValue = id;
            return this;
        }

        public EmployeeBuilder constructName(String name) {
            nameValue = name;
            return this;
        }

        public Employee createEmployee() {
            return new Employee(idValue, nameValue);
        }
    }
}
