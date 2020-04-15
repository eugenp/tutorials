package com.baeldung.singleton;

public enum EnumSingleton {
    
    INSTANCE("Initial enum info"); //Name of the single instance
    
    private String info;
    
    private EnumSingleton(String info) {
        this.info = info;
    }
    
    public EnumSingleton getInstance(){
        return INSTANCE;
    }
    
    //getters and setters

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }    
}
