package com.baeldung.hexagonal.ports;

public interface ILogMessage {

    public boolean logMessage(int Severity, String message);

}
