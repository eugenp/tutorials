package com.baeldung.persistjson;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class Specification implements Serializable  {

    private int ram;
    private int internalMemory;
    private String processor;

    public Specification() {
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getInternalMemory() {
        return internalMemory;
    }

    public void setInternalMemory(int internalMemory) {
        this.internalMemory = internalMemory;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }
}
