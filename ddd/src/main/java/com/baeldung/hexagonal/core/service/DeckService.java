package com.baeldung.hexagonal.core.service;

import com.baeldung.hexagonal.core.model.Card;
import com.baeldung.hexagonal.core.model.Deck;
import java.util.Collection;

public interface DeckService {

    void addCard(long deckId, Card card);

    void addAllCards(long deckId, Collection<Card> cards);

    void removeCard(long deckId, Card card);

    Deck getDeck(long deckId);

}
