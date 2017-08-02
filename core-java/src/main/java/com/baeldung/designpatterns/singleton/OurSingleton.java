package com.baeldung.designpatterns.singleton;

public class OurSingleton {

    private static final OurSingleton INSTANCE = new OurSingleton();
    private String info = "Initial info";
    
    private OurSingleton(){        
    }
    
    public static OurSingleton getInstance(){
        return INSTANCE;
    }
    
    //other getters and setters
    
    public String getInfo() {
        return info;
    }
    
    public void setInfo(String info) {
        this.info = info;
    }
}
