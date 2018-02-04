package com.baeldung.dependencyinjectiontypes;

public class ExampleInjection {

    private String strField;
    private int intField;
    
    public ExampleInjection() {
        super();
    }

    public ExampleInjection(String strField) {
        this.strField = strField;
        this.intField = 0;
    }

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
