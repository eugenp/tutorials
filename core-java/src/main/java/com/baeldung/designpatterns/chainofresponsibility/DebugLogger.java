package com.baeldung.designpatterns.chainofresponsibility;

import java.io.IOException;
import java.io.OutputStream;

public class DebugLogger extends AbstractLogger {
    public DebugLogger(int level, AbstractLogger nextLogger) {
        super.setNextLogger(nextLogger);
        this.level = level;
    }
    
    @Override
    public void write(String message, OutputStream os) throws IOException{
        os.write(new String("DEBUG::Logger: " + message).getBytes());
    }
}
