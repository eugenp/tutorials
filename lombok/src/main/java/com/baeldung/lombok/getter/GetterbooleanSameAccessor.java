package com.baeldung.lombok.getter;


import lombok.Getter;

/**
 * Related Article Sections:
 * 3.2. Two boolean Fields With the Same Accessor Name
 *
 */
public class GetterbooleanSameAccessor {

    @Getter
    public boolean running = true;

    @Getter
    public boolean isRunning = false;
}
