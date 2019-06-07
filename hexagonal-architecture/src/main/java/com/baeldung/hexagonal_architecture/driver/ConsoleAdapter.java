package com.baeldung.hexagonal_architecture.driver;

import com.baeldung.hexagonal_architecture.core.RequestFacts;

public class ConsoleAdapter {
    private RequestFacts requestfacts;

    public ConsoleAdapter(RequestFacts requestfacts) {

        this.requestfacts = requestfacts;

    }

    public String askForFacts() {

        return requestfacts.askForFacts();
    }
}
