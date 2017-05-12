package com.baeldung.spring.dependency.injection.beans;

public class Singer {
    private String name;

    public Singer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void printSingerName(){
    	System.out.println("Singer Name : "+name);
    }
}
