package com.baeldung.jackson.defaultvalues;

public class NonAnnotatedDefaultValue {

    private String required;
    private String optional = "defaultValue";

    public String getRequired() {
        return required;
    }

    public String getOptional() {
        return optional;
    }

    @Override
    public String toString() {
        return "NonAnnotatedDefaultValue{" + "required='" + required + '\'' + ", optional='" + optional + '\'' + '}';
    }
}
