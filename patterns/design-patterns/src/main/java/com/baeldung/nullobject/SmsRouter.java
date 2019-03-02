package com.baeldung.nullobject;

public class SmsRouter implements Router {

    @Override
    public void route(Message msg) {
        System.out.println("Routing to a SMS gate. Msg: " + msg);
    }

}
