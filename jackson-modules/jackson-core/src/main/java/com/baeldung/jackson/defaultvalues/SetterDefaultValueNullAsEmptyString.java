package com.baeldung.jackson.defaultvalues;

public class SetterDefaultValueNullAsEmptyString {

    private String required;
    private String optional = "valueIfMissingEntirely";

    public void setOptional(String optional){
        if(optional == null){
            this.optional = "";
        }
    }

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
