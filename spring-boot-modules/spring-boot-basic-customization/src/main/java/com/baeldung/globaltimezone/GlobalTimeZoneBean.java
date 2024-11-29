package com.baeldung.globaltimezone;

import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GlobalTimeZoneBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalTimeZoneBean.class);

    private final String globalTimeZone;

    public GlobalTimeZoneBean() {
        // TimeZone.setDefault(TimeZone.getTimeZone("GMT+08:00"));
        LOGGER.info("Default timezone, during beans creation, is set to: " + TimeZone.getDefault()
            .getDisplayName());

        globalTimeZone = TimeZone.getDefault()
            .getDisplayName();
    }

    public String getGlobalTimeZone() {
        return globalTimeZone;
    }
}
