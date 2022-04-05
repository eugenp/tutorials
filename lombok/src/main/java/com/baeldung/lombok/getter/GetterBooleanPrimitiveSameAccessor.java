package com.baeldung.lombok.getter;


import lombok.Getter;

/**
 * Related Article Sections:
 * 3.2. Two boolean Fields With the Same Accessor Name
 *
 */
public class GetterBooleanPrimitiveSameAccessor {

    @Getter
    boolean running = true;

    @Getter
    boolean isRunning = false;
}
