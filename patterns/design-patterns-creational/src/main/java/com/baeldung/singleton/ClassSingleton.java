package com.baeldung.singleton;

public class ClassSingleton {

    private static ClassSingleton INSTANCE;
    private String info = "Initial class info";
    
    private ClassSingleton(){        
    }
    
    public static ClassSingleton getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ClassSingleton();
        }
        
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
