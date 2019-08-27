package com.baeldung.patterns.hexagonal.module;

import com.baeldung.patterns.hexagonal.domain.GreekNameGenerator;
import com.baeldung.patterns.hexagonal.domain.NameGenerator;
import com.baeldung.patterns.hexagonal.driver.ConsoleDriver;
import com.google.inject.AbstractModule;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ConsoleDriver.class).toInstance(new ConsoleDriver());
        bind(NameGenerator.class).toInstance(new GreekNameGenerator());
    }

}
