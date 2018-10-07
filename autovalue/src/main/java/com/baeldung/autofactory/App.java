package com.baeldung.autofactory;

import com.baeldung.autofactory.model.Camera;
import com.baeldung.autofactory.model.Phone;
import com.baeldung.autofactory.model.PhoneFactory;
import com.baeldung.autofactory.modules.SonyCameraModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class App {

    public static void main(String[] args) {
        PhoneFactory phoneFactory = new PhoneFactory(() -> new Camera("Unknown", "XXX"));
        Phone simplePhone = phoneFactory.create("other parts");

        Injector injector = Guice.createInjector(new SonyCameraModule());
        PhoneFactory injectedFactory = injector.getInstance(PhoneFactory.class);
        Phone xperia = injectedFactory.create("Xperia");
    }

}
