package com.baeldung.hexagonal.core.service;

import com.baeldung.hexagonal.core.model.Card;
import com.baeldung.hexagonal.core.model.Deck;
import com.baeldung.hexagonal.core.repository.DeckRepository;
import java.util.Set;

public class DeckDomainService implements DeckService {

    private final DeckRepository deckRepository;

    public DeckDomainService(final DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    @Override
    public void addCard(final long deckId, final Card card) {
        Deck deck = deckRepository.getDeckById(deckId);
        deck.add(card);
        deckRepository.updateDeck(deck);
    }

    @Override
    public void addAllCards(final long deckId, final Set<Card> cards) {
        Deck deck = deckRepository.getDeckById(deckId);
        deck.addAll(cards);
        deckRepository.updateDeck(deck);
    }

    @Override
    public void removeCard(final long deckId, final Card card) {
        Deck deck = deckRepository.getDeckById(deckId);
        deck.remove(card);
        deckRepository.updateDeck(deck);
    }

    @Override
    public Deck getDeck(final long deckId) {
        return deckRepository.getDeckById(deckId);
    }
}
