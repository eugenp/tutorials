package com.baeldung.hexagonal.core.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Deck {

    private final Set<Card> cards;

    public Deck() {
        cards = new HashSet<>();
    }

    public Deck(Card ... cards) {
        this.cards = new HashSet<>(Arrays.asList(cards));
    }

    public boolean add(final Card card) {
        return cards.add(card);
    }

    public boolean addAll(final Collection<Card> cards) {
        return this.cards.addAll(cards);
    }

    public boolean remove(final Card card) {
        return cards.remove(card);
    }

    public boolean doesNotContain(final Card card) {
        return !cards.contains(card);
    }

    public boolean contains(final Card card) {
        return cards.contains(card);
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

