package com.baeldung.hexagonalarchitecture.test;

import com.baeldung.hexagonalarchitecture.application.ports.LoggerPort;

import java.util.ArrayList;
import java.util.List;

public class FakeTestLogger implements LoggerPort {
    private List<String> logMessages = new ArrayList<>();

    @Override
    public void writeLog(String message) {
        logMessages.add(message);
    }

    public boolean contains(String message) {
        return logMessages.contains(message);
    }
}
