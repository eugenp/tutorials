package com.baeldung.designpatterns.chainofresponsibility;

import java.io.IOException;
import java.io.OutputStream;

public class ErrorLogger extends AbstractLogger {
    public ErrorLogger(int level, AbstractLogger nextLogger) {
        super.setNextLogger(nextLogger);
        this.level = level;
    }
    
    @Override
    public void write(String message, OutputStream os) throws IOException{
        
        os.write(new String("ERROR::Logger: " + message).getBytes());
        
    }
}
