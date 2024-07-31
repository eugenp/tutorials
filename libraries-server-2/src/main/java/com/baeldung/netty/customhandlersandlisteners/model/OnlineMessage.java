package com.baeldung.netty.customhandlersandlisteners.model;

public class OnlineMessage extends Message {

    public OnlineMessage(String info) {
        super("system", "client online: " + info);
    }
}
