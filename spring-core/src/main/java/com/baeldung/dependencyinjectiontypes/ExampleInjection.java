package com.baeldung.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

public class ExampleInjection {

    private String strField;
    private int intField;
    
    public ExampleInjection() {
        super();
    }
    
    @Autowired
    public ExampleInjection(String strField, int intField) {
        this.strField = strField;
        this.intField = intField;
    }

    public String getStrField() {
        return strField;
    }
    
    public void setStrField(String strField) {
        this.strField = strField;
    }
    
    public int getIntField() {
        return intField;
    }
    
    public void setIntField(int intField) {
        this.intField = intField;
    }

    @Override
    public String toString() {
        return "ExampleInjection [strField=" + strField + ", intField=" + intField + "]";
    }
    
}
