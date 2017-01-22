package com.baeldung.fieldinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.constructordi.domain.Engine;
import com.baeldung.constructordi.domain.Transmission;

@Component
public class Bike {

	@Autowired
	private Engine engine;
    @Autowired
    private Transmission transmission;

    @Override
    public String toString() {
        return String.format("Engine: %s Transmission: %s", engine, transmission);
    }
}
