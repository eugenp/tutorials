package com.baeldung.differenttypesdi.explicit.domain;

import com.baeldung.differenttypesdi.shared.Processor;
import com.baeldung.differenttypesdi.shared.Signal;

public class ControlSignalReceptor {

    private Processor processor;

    public ControlSignalReceptor(Processor processor) {
        this.processor = processor;
    }

    public void receive(Signal signal) {
        if (signal == Signal.NEXT_CHANNEL) {
            this.processor.setNextChannel();
        } else if (signal == Signal.PREV_CHANNEL) {
            this.processor.setPrevChannel();
        }
    }

}
