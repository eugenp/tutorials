package com.baeldung.designpatterns.singleton;

public enum EnumSingleton {
    
    INSTANCE; //Name of the single instance
    
    private String info = "Initial enum info";
    
    private EnumSingleton() {
    }
    
    //getters and setters

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }    
}
