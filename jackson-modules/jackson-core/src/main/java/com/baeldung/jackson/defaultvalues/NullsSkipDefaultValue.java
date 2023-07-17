package com.baeldung.jackson.defaultvalues;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

public class NullsSkipDefaultValue {

    private String required;
    @JsonSetter(nulls = Nulls.SKIP)
    private String optional = "defaultValue";

    public void setOptional(String optional){}

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
