package com.baeldung.differenttypesdi.autowire.domain;

import com.baeldung.differenttypesdi.shared.Processor;
import com.baeldung.differenttypesdi.shared.Signal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Television {

    private Processor processor;

    private ControlSignalReceptor controlSignal;

    @Autowired
    public Television(Processor processor, ControlSignalReceptor controlSignal) {
        this.controlSignal = controlSignal;
        this.processor = processor;
    }

    public Integer showCurrentChannel() {
        return processor.getCurrentChannel();
    }

    public void receiveSignal(Signal signal) {
        controlSignal.receive(signal);
    }

}
