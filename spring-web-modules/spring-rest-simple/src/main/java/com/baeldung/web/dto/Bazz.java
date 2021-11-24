package com.baeldung.web.dto;

public class Bazz {

    
    public String id;
    public String name;

    public Bazz(String id){
        this.id = id;
    }
    public Bazz(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Bazz [id=" + id + ", name=" + name + "]";
    }
    
}
