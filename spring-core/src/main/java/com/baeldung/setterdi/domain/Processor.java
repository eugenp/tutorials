/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.setterdi.domain;

/**
 *
 * @author matteoroxis
 */
public class Processor {
    
    private String type;
    
    public Processor(){
        
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return String.format("%s", type);
    }
    
}
