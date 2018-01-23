package com.baeldung.spring.tutorial;

/**
 * Class that will receive the {@link InjectedObject} via the Constructor.
 */
public class ConstructorInjectionReceiver {

    /**
     * Injected Object
     */
    private final InjectedObject internalConstructorInjectedObject;

    /**
     * Constructor.
     *
     * @param theInjectedObject Injected object in the receiver
     */
    public ConstructorInjectionReceiver(InjectedObject theInjectedObject) {
        this.internalConstructorInjectedObject = theInjectedObject;
    }

    /**
     * Getter for the SetterInjectedObject Name.
     *
     * @return the name of the SetterInjectedObject
     */
    public String getConstructorInjectedObjectName() {
        return this.internalConstructorInjectedObject.getInjectedObjectName();
    }
}
