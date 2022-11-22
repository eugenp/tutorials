package com.baeldung.lombok.jackson;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder(builderClassName = "EmployeeBuilder")
@Jacksonized
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
