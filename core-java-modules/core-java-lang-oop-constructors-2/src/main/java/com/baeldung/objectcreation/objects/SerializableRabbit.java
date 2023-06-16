package com.baeldung.objectcreation.objects;

import java.io.Serializable;

public class SerializableRabbit implements Serializable {
    
    private static final long serialVersionUID = 2589844379773087465L;
    
    String name = "";
    
    public SerializableRabbit() {
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
