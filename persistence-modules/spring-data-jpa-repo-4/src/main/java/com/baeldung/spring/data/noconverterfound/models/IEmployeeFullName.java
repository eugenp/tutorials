package com.baeldung.spring.data.noconverterfound.models;

public interface IEmployeeFullName {
    String getFirstName();

    String getLastName();

    default String fullName() {
        return getFirstName().concat(" ")
            .concat(getLastName());
    }
}
