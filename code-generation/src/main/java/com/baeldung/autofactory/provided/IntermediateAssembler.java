package com.baeldung.autofactory.provided;

import com.baeldung.autofactory.model.Camera;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;


import javax.inject.Provider;

/**
 * @author aiet
 */
@AutoFactory
public class IntermediateAssembler {

    private final Provider<Camera> camera;
    private final String otherParts;

    public IntermediateAssembler(@Provided Provider<Camera> camera, String otherParts) {
        this.camera = camera;
        this.otherParts = otherParts;
    }
}
