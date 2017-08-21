package com.baeldung.concurrent.volatilekeyword;


public class SharedObject {
    private volatile int count=0;

    void increamentCount(){
        count++;
    }
    public int  getCount(){
        return count;
    }
}
