/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.constructordi.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author matteoroxis
 */
@Component
public class Telephone {

    private Processor processor;

    @Autowired
    public Telephone(Processor processor) {
        this.processor = processor;
    }

    @Override
    public String toString() {
        return String.format("Processor: %s", processor);
    }

}
