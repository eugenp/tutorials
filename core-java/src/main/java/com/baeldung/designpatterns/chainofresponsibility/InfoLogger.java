package com.baeldung.designpatterns.chainofresponsibility;

import java.io.IOException;
import java.io.OutputStream;

public class InfoLogger extends AbstractLogger {
    public InfoLogger(int level, AbstractLogger nextLogger) {
        super.setNextLogger(nextLogger);
        this.level = level;
    }
    
    @Override
    public void write(String message, OutputStream os) throws IOException {
        
        os.write(new String("INFO::Logger: " + message).getBytes());
    }
}
