package com.baeldung.examples;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import com.baeldung.examples.guice.provider.MyGuiceModule;
import com.baeldung.examples.guice.provider.Notifier;
import com.baeldung.examples.guice.provider.EmailNotifier;
import com.baeldung.examples.guice.provider.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceProviderTester {
    @Test
    public void givenGuiceProvider_whenInjecting_thenShouldReturnEmailNotifier() {
        // Create a Guice injector with the NotifierModule
        Injector injector = Guice.createInjector(new MyGuiceModule());
        // Get an instance of Notifier from the injector
        Notifier notifier = injector.getInstance(Notifier.class);
        // Assert that notifier is of type EmailNotifier
        assert notifier != null;
        assert notifier instanceof EmailNotifier;
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
