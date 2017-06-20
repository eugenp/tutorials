package com.baeldung.springsecuredsockets.transfer.pet;

public class PetTransfer {

    private String name;
    private String type;
    private String description;

    public PetTransfer(String name, String type, String description) {
        setName(name);
        setType(type);
        setDescription(description);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


