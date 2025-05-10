package com.baeldung.java;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import com.baeldung.examples.guice.provider.MyGuiceModule;
import com.baeldung.examples.guice.provider.Notifier;
import com.baeldung.examples.guice.provider.EmailNotifier;
import com.baeldung.examples.guice.provider.Logger;

import com.baeldung.examples.guice.provider.PhoneNotifier;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceProviderUnitTest {
    @Test
    public void givenGuiceProvider_whenInjecting_thenShouldReturnEmailNotifier() {
        // Create a Guice injector with the NotifierModule
        Injector injector = Guice.createInjector(new MyGuiceModule());
        // Get an instance of Notifier from the injector
        Notifier emailNotifier = injector.getInstance(EmailNotifier.class);
        Notifier phoneNotifier = injector.getInstance(PhoneNotifier.class);
        // Assert that notifier is of type EmailNotifier
        assert emailNotifier != null;

        assert emailNotifier instanceof EmailNotifier;
        assert phoneNotifier instanceof PhoneNotifier;
    }

    @Test
    public void givenGuiceProvider_whenInjectingWithProvides_thenShouldReturnCustomLogger() {
        // Create a Guice injector with the NotifierModule
        Injector injector = Guice.createInjector(new MyGuiceModule());
        // Get an instance of Logger from the injector
        Logger logger = injector.getInstance(Logger.class);
        assert logger != null;
        Assertions.assertNotNull(logger.log("Hello world"));
    }
}
