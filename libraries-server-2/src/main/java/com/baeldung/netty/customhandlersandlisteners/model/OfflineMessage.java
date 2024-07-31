package com.baeldung.netty.customhandlersandlisteners.model;

public class OfflineMessage extends Message {

    public OfflineMessage(String info) {
        super("system", "client went offline: " + info);
    }
}
