package com.baeldung.reactive.model;

public class FruitStatus {
    
    private String name;
    private String status;
    
    public FruitStatus() {
        
    }
    
    public FruitStatus(String name, String status) {
        super();
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FruitStatus [name=" + name + ", status=" + status + "]";
    }
    
}
