package com.baeldung.properties.value;

public class ClassNotManagedBySpring {

    private String customVariable;
    private String anotherCustomVariable;

    public ClassNotManagedBySpring(String someInitialValue, String anotherManagedValue) {
        this.customVariable = someInitialValue;
        this.anotherCustomVariable = anotherManagedValue;
    }

    public String getCustomVariable() {
        return customVariable;
    }

    public void setCustomVariable(String customVariable) {
        this.customVariable = customVariable;
    }

    public String getAnotherCustomVariable() {
        return anotherCustomVariable;
    }

    public void setAnotherCustomVariable(String anotherCustomVariable) {
        this.anotherCustomVariable = anotherCustomVariable;
    }
}
