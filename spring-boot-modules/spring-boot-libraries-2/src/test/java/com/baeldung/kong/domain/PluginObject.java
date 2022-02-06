package com.baeldung.kong.domain;

/**
 * @author aiet
 */
public class PluginObject {

    private String name;
    private String consumer_id;

    public PluginObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConsumer_id() {
        return consumer_id;
    }

    public void setConsumer_id(String consumer_id) {
        this.consumer_id = consumer_id;
    }
}
