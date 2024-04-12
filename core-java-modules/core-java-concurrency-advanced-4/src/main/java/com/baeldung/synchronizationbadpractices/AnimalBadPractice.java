package com.baeldung.synchronizationbadpractices;

public class AnimalBadPractice {
    
    private String name;
    private String owner;
    
    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }
    
    public void setOwner(String owner) {
        synchronized(this) {
            this.owner = owner;
        }
    }
    
    public AnimalBadPractice() {
        
    }
    
    public AnimalBadPractice(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }
    
}
