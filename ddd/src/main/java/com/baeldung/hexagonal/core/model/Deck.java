package com.baeldung.hexagonal.core.model;

import java.util.Set;

public class Deck {

    private final Set<Card> cards;

    public Deck(final Set<Card> cards) {
        this.cards = cards;
    }

    public boolean add(final Card card) {
        return cards.add(card);
    }

    public boolean addAll(final Set<Card> cards) {
        return this.cards.addAll(cards);
    }

    public boolean remove(final Card card) {
        return cards.remove(card);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Deck deck = (Deck) o;

        return cards != null ? cards.equals(deck.cards) : deck.cards == null;
    }

    @Override
    public int hashCode() {
        return cards != null ? cards.hashCode() : 0;
    }
}

