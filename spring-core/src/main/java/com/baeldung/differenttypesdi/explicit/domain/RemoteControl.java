package com.baeldung.differenttypesdi.explicit.domain;

import com.baeldung.differenttypesdi.shared.Control;
import com.baeldung.differenttypesdi.shared.Signal;

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
