package com.baeldung.kong.domain;

/**
 * @author aiet
 */
public class ConsumerObject {

    private String username;
    private String custom_id;

    public ConsumerObject(String username) {
        this.username = username;
    }

    public ConsumerObject(String username, String custom_id) {
        this.username = username;
        this.custom_id = custom_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCustom_id() {
        return custom_id;
    }

    public void setCustom_id(String custom_id) {
        this.custom_id = custom_id;
    }
}
