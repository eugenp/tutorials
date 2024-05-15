package com.baeldung.synchronizationbadpractices;

public class AnimalSolution { 
    
    private final Object objLock1 = new Object();
    private final Object objLock2 = new Object();
    
    private String name;
    private String owner;
    
    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }
    
    
    public void setName(String name) {
        synchronized(objLock1) {
            this.name = name;
        }
    }
     
    public void setOwner(String owner) {
        synchronized(objLock2) {
            this.owner = owner;
        }
    }
    
    public AnimalSolution() {
        
    }
    
    public AnimalSolution(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }
    

}