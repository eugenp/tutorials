package com.baeldung.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>MessageService</code>.
 */
public interface MessageServiceAsync {
    void sendMessage(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
}
