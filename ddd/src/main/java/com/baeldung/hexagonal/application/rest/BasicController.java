package com.baeldung.hexagonal.application.rest;

import com.baeldung.hexagonal.core.service.DeckService;

public class BasicController {

    private final DeckService deckService;

    public BasicController(final DeckService deckService) {
        this.deckService = deckService;
    }
}
