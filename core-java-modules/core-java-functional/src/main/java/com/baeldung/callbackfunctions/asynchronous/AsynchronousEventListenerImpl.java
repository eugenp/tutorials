package com.baeldung.callbackfunctions.asynchronous;

import com.baeldung.callbackfunctions.EventListener;

public class AsynchronousEventListenerImpl implements EventListener {

    @Override
    public String onTrigger()
    {
        return "Asynchronously running callback function";
    }

}
