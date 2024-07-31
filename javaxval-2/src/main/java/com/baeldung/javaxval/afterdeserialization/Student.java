package com.baeldung.javaxval.afterdeserialization;

import jakarta.validation.constraints.Size;

public class Student {

    @Size(min = 5, max = 10, message = "Student's name must be between 5 and 10 characters")
    private String name;

    public String getName() {
        return name;
    }

}
