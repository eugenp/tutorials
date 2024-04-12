package com.baeldung.autofactory.model;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;

import javax.inject.Named;

/**
 * @author aiet
 */
@AutoFactory
public class Phone {

    private Camera camera;
    private String otherParts;

    public Phone(@Provided @Named("Sony") Camera camera, String otherParts) {
        this.camera = camera;
        this.otherParts = otherParts;
    }

    /* required when used as a base class for AutoFactory */
    public Phone() {
    }

    public Camera getCamera() {
        return camera;
    }

    public String getOtherParts() {
        return otherParts;
    }

}
