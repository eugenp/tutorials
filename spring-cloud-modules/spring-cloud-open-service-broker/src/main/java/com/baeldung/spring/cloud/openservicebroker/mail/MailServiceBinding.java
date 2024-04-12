package com.baeldung.spring.cloud.openservicebroker.mail;

import java.util.Map;

public class MailServiceBinding {

    private String bindingId;
    private Map<String, Object> credentials;

    public MailServiceBinding(String bindingId, Map<String, Object> credentials) {
        this.bindingId = bindingId;
        this.credentials = credentials;
    }

    public String getBindingId() {
        return bindingId;
    }

    public Map<String, Object> getCredentials() {
        return credentials;
    }
}
