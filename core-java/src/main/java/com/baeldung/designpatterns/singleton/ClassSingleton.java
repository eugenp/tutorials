package com.baeldung.designpatterns.singleton;

public class ClassSingleton {

    private static final ClassSingleton INSTANCE = new ClassSingleton();
    private String info = "Initial class info";
    
    private ClassSingleton(){        
    }
    
    public static ClassSingleton getInstance(){
        return INSTANCE;
    }
    
    // getters and setters
    
    public String getInfo() {
        return info;
    }
    
    public void setInfo(String info) {
        this.info = info;
    }
}
