package com.baeldung.spring.tutorial;

/**
 * Class that will receive the SetterInjectedObject.
 */
public class SetterInjectionReceiver {

    /**
     * SetterInjectedObject
     */
    private InjectedObject internalSetterInjectedObject;

    /**
     * Getter for the SetterInjectedObject Name.
     *
     * @return the name of the SetterInjectedObject
     */
    public String getSetterInjectedObjectName() {
        return this.internalSetterInjectedObject.getInjectedObjectName();
    }

    /**
     * Setter for the {@link InjectedObject}.
     *
     * @param internalSetterInjectedObject The SetterInjectedObject to set
     */
    public void setInternalSetterInjectedObject(InjectedObject internalSetterInjectedObject) {
        this.internalSetterInjectedObject = internalSetterInjectedObject;
    }
}
