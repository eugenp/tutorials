/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.setterdi.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author matteoroxis
 */
@Component
public class Telephone {

    private Processor processor;

    public Telephone() {
    }

    @Autowired
    public void setProcessor(Processor processor) {
        this.processor = processor;
    }
    
    @Override
    public String toString() {
        return String.format("Processor: %s", processor);
    }

}
