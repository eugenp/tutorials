package com.baeldung.spring.tutorial;

/**
 * This bean will be injected during the test.
 */
public class InjectedObject {

    /**
     * Value to be returned.
     */
    private static final String INJECTED_OBJECT_NAME = "Injected Object";

    /**
     * Getter for the Injected Object Name.
     *
     * @return The name of the {@link InjectedObject}
     */
    public String getInjectedObjectName() {
        return InjectedObject.INJECTED_OBJECT_NAME;
    }
}
