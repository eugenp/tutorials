package com.baeldung.hexagonal.core.repository;

import com.baeldung.hexagonal.core.model.Deck;

public interface DeckRepository {

    Deck getDeckById(long id);

    void updateDeck(Deck deck);
}
