package com.baeldung.pattern.hexagonal.ui;

import com.baeldung.pattern.hexagonal.domain.UIActionPort;

public class ConsoleUIAdapter {
    UIActionPort uiActionPort;

    public ConsoleUIAdapter(final UIActionPort uiActionPort) {
        this.uiActionPort = uiActionPort;
    }

    public String display(){
        return uiActionPort.executeSomeAction("Console Adapter");
    }
}
