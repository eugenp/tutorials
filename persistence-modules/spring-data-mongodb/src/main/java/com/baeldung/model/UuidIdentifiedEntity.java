package com.baeldung.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;

public abstract class UuidIdentifiedEntity {

    @Id   
    protected UUID id;    

    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        
        if(this.id != null) {
            
            throw new UnsupportedOperationException("ID is already defined");
        }
        
        this.id = id;
    }
}
