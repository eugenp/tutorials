package com.baeldung.callbackfunctions.synchronous;

import com.baeldung.callbackfunctions.EventListener;

public class SynchronousEventListenerImpl implements EventListener {

    @Override
    public String onTrigger()
    {
        return "Synchronously running callback function";
    }

    @Override
    public void respondToTrigger(){
        System.out.println("Response to trigger");
    }

}
