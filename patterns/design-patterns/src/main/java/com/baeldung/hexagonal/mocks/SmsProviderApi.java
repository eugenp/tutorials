package com.baeldung.hexagonal.mocks;

import java.util.Map;

@SuppressWarnings({ "SpellCheckingInspection", "unused" })
public class SmsProviderApi {

    public void send(String handle, Map<String, String> data) {
        /* DO THE ACTUAL SENDING */
        System.out.println("Sending notification to " + handle);
    }

}
