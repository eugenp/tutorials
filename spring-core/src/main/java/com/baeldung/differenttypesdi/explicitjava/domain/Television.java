package com.baeldung.differenttypesdi.explicitjava.domain;

import com.baeldung.differenttypesdi.shared.Processor;
import com.baeldung.differenttypesdi.shared.Signal;

public class Television {

    private Processor processor;

    private ControlSignalReceptor controlSignal;

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
