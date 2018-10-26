package com.baeldung.hexagonaldesign.impl;

import com.baeldung.hexagonaldesign.Display;

public class ConsoleDisplay implements Display {
    @Override
    public void show(String text) {
        System.out.println(text);
    }
}
