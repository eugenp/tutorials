package com.baeldung.hexagonal.core.service;

import com.baeldung.hexagonal.core.model.Card;
import com.baeldung.hexagonal.core.model.Deck;
import com.baeldung.hexagonal.core.repository.DeckRepository;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DeckDomainService implements DeckService {

    private final DeckRepository deckRepository;

    public DeckDomainService(final DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    @Override
    public void addCard(final long deckId, final Card card) {
        Deck deck = deckRepository.getDeckById(deckId);
        if (deck.doesNotContain(card)) {
            deck.add(card);
            deckRepository.updateDeck(deck);
        } else {
            // Here a place where some additional logic can be implemented
            // if we're trying to insert a card that already exists in the deck
        }
    }

    @Override
    public void addAllCards(final long deckId, final Collection<Card> cards) {
        Deck deck = deckRepository.getDeckById(deckId);
        Set<Card> filteredCards = filterExistentCards(cards, deck);
        if (!filteredCards.isEmpty()) {
            deck.addAll(filteredCards);
            deckRepository.updateDeck(deck);
        } else {
            // Here a place where some additional logic can be implemented
            // if we're trying to add either an empty list of cards, or all the cards already present in the deck
        }
    }

    @Override
    public void removeCard(final long deckId, final Card card) {
        Deck deck = deckRepository.getDeckById(deckId);
        if (deck.contains(card)) {
            deck.remove(card);
            deckRepository.updateDeck(deck);
        } else {
            // Here a place where some additional logic can be implemented
            // if we're trying to remove a card that is not in a deck
        }
    }

    @Override
    public Deck getDeck(final long deckId) {
        return deckRepository.getDeckById(deckId);
    }

    private Set<Card> filterExistentCards(final Collection<Card> cards, final Deck deck) {
        Set<Card> filteredCards = new HashSet<>();
        for (final Card card : cards) {
            if (deck.doesNotContain(card)) {
                filteredCards.add(card);
            }
        }
        return filteredCards;
    }
}
