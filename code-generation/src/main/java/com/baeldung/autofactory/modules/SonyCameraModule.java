package com.baeldung.autofactory.modules;

import com.baeldung.autofactory.model.Camera;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import javax.inject.Named;

/**
 * @author aiet
 */
public class SonyCameraModule extends AbstractModule {

    private static int SONY_CAMERA_SERIAL = 1;

    @Named("Sony")
    @Provides
    Camera cameraProvider() {
        return new Camera("Sony", String.format("%03d", SONY_CAMERA_SERIAL++));
    }

}
