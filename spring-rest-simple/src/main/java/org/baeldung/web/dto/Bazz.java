package org.baeldung.web.dto;

public class Bazz {

    
    private String id;
    private String name;

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
