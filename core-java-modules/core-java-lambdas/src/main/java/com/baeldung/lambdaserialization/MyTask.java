package com.baeldung.lambdaserialization;

import java.io.Serializable;

public class MyTask implements Runnable, Serializable {
    
    
    public MyTask() {
        
    }
    
    public void run() {
        System.out.println("Serialized using class ");
    }
}
