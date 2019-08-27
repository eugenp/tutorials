package com.baeldung.patterns.hexagonal.driver;

import com.baeldung.patterns.hexagonal.domain.NameGenerator;
import com.google.inject.Inject;

public class ConsoleDriver {

    @Inject
    private NameGenerator nameGenerator;

    public void generateUser() {
        String name = nameGenerator.getName(6);
    }

}
