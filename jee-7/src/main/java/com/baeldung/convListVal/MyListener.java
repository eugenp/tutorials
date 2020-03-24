package com.baeldung.convListVal;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

public class MyListener implements ValueChangeListener {

    private static final Logger LOG = Logger.getLogger(MyListener.class.getName());	
	@Override
	public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
        if (event.getNewValue() != null) {
            LOG.log(Level.INFO, "\tNew Value:{0}", event.getNewValue());
        }

	}

}
