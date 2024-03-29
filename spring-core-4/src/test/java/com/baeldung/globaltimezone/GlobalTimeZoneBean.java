package com.baeldung.globaltimezone;

import java.util.TimeZone;

public class GlobalTimeZoneBean {

    private final String globalTimeZone;

    public GlobalTimeZoneBean() {
        this.globalTimeZone = TimeZone.getDefault()
            .getDisplayName();
    }

    public String getGlobalTimeZone() {
        return globalTimeZone;
    }
}
