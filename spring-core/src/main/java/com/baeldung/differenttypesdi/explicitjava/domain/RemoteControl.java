package com.baeldung.differenttypesdi.explicitjava.domain;

import com.baeldung.differenttypesdi.shared.Control;
import com.baeldung.differenttypesdi.shared.Signal;
import org.springframework.stereotype.Component;

public class RemoteControl implements Control {

    private Signal pressedButton;

    @Override
    public void pressButton(Signal signal) {
        this.pressedButton = signal;
    }

    @Override
    public Signal emitSignal() {
        return pressedButton;
    }

}
