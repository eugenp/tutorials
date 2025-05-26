package com.baeldung.guice.provider;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

public class MyGuiceModule extends AbstractModule {
    /**
     * This method is called when the Guice injector is created.
     * It binds the Notifier interface to the EmailNotifier implementation.
     */

    @Override
    protected void configure() {
        bind(Notifier.class).annotatedWith(Names.named("Email"))
          .toProvider(EmailNotifier.class);

        bind(Notifier.class).annotatedWith(Names.named("Phone"))
          .toProvider(PhoneNotifier.class);
    }

    @Provides
    public Logger provideLogger() {
        return new Logger() {
            @Override
            public String log(String message) {
                return "Logging message: " + message;
            }
        };
    }
}
