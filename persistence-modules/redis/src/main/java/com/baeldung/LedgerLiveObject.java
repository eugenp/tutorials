package com.baeldung;

import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;

/**
 * Created by johnson on 3/9/17.
 */
@REntity
public class LedgerLiveObject {
    @RId
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
