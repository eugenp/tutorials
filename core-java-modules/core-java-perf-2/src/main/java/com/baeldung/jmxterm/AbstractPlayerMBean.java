package com.baeldung.jmxterm;

import java.util.UUID;

public abstract class AbstractPlayerMBean implements PlayerMBean{

    private final UUID id;

    protected AbstractPlayerMBean() {
        this.id = UUID.randomUUID();
    }

    String getId() {
        return getName() + id.toString();
    }
}
