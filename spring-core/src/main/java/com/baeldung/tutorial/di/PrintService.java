package com.baeldung.tutorial.di;

import org.springframework.beans.factory.annotation.Autowired;

public class PrintService {

    @Autowired
    private String message;
    
    public PrintService(String message) {
        this.message = message;
    }
    
    public PrintService() {
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String printMessage() {
        System.out.println(message);
        return message;
    }
}
