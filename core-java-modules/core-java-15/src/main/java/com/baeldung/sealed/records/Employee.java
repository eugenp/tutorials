package com.baeldung.sealed.records;

import org.apache.commons.lang3.StringUtils;

public record Employee(String firstName, String lastName, String employeeId) implements Person {

    @Override
    public String getSalutation() {
        return StringUtils.join("Dear", " ", firstName, " ", lastName);
    }

    @Override
    public String getId() {
        return employeeId;
    }
    
}
