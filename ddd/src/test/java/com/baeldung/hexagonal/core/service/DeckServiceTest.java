package com.baeldung.hexagonal.core.service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.baeldung.hexagonal.core.model.Card;
import com.baeldung.hexagonal.core.model.Deck;
import com.baeldung.hexagonal.core.repository.DeckRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeckServiceTest {


    private static final int DECK_ID = 1;
    public static final Card FIRST_CARD = new Card("Is it possible to instantiate interfaces in Java?",
        "No, interfaces are used as abstractions, and need concrete classes to implement them");
    public static final Card SECOND_CARD = new Card("Who came up with the term Onion architecture?",
        "Jeffrey Palermo in 2008");
    public static final Card THIRD_CARD = new Card("What DDD stands for?",
        "Domain Driven Design");
    private DeckRepository deckRepository;
    private DeckService deckService;

    @Test
    @DisplayName("Card added to a deck successfully")
    void addCardSuccess() {
        // given
        Deck deck = new Deck();
        when(deckRepository.getDeckById(DECK_ID)).thenReturn(deck);
        // when
        deckService.addCard(DECK_ID, FIRST_CARD);
        // then
        verify(deckRepository).getDeckById(DECK_ID);
        verify(deckRepository).updateDeck(deck);

    }

    @Test
    @DisplayName("Card wasn't added to a deck if it's already exists there")
    void addSameCardFail() {
        // given
        Deck deck = new Deck(FIRST_CARD, SECOND_CARD, THIRD_CARD);
        when(deckRepository.getDeckById(DECK_ID)).thenReturn(deck);
        // when
        deckService.addCard(DECK_ID, FIRST_CARD);
        // then
        verify(deckRepository).getDeckById(DECK_ID);
        verify(deckRepository, never()).updateDeck(deck);
    }

    @Test
    @DisplayName("All the cards were added to the deck")
    void addAllCardsSuccess() {
        // given
        HashSet<Card> cards = new HashSet<>(Arrays.asList(SECOND_CARD, THIRD_CARD));
        Deck deck = new Deck(FIRST_CARD);
        when(deckRepository.getDeckById(DECK_ID)).thenReturn(deck);
        // when
        deckService.addAllCards(DECK_ID, cards);
        // then
        verify(deckRepository).getDeckById(DECK_ID);
        verify(deckRepository).updateDeck(deck);
    }

    @Test
    @DisplayName("Cards that are present in the deck won't be added to it")
    void addAllCardsThatAlreadyPresentFail() {
        // given
        HashSet<Card> cards = new HashSet<>(Arrays.asList(FIRST_CARD, SECOND_CARD));
        Deck deck = new Deck(FIRST_CARD, SECOND_CARD);
        when(deckRepository.getDeckById(DECK_ID)).thenReturn(deck);
        // when
        deckService.addAllCards(DECK_ID, cards);
        // then
        verify(deckRepository).getDeckById(DECK_ID);
        verify(deckRepository, never()).updateDeck(deck);

    }

    @Test
    @DisplayName("Adding empty collection of cards is ignored")
    void addEmptyListOfCardsFail() {
        // given
        Deck deck = new Deck();
        when(deckRepository.getDeckById(DECK_ID)).thenReturn(deck);
        // when
        deckService.addAllCards(DECK_ID, Collections.emptySet());
        // then
        verify(deckRepository).getDeckById(DECK_ID);
        verify(deckRepository, never()).updateDeck(deck);
    }

    @Test
    @DisplayName("Card was successfully removed")
    void removeCardSuccess() {
        // given
        Deck deck = new Deck(FIRST_CARD, SECOND_CARD, THIRD_CARD);
        when(deckRepository.getDeckById(DECK_ID)).thenReturn(deck);
        // when
        deckService.removeCard(DECK_ID, FIRST_CARD);
        // then
        verify(deckRepository).getDeckById(DECK_ID);
        verify(deckRepository).updateDeck(deck);
    }

    @Test
    @DisplayName("Card that isn't in the deck cannot be removed")
    void removeNonExistentCardFail() {
        // given
        Deck deck = new Deck(SECOND_CARD, THIRD_CARD);
        when(deckRepository.getDeckById(DECK_ID)).thenReturn(deck);
        // when
        deckService.removeCard(DECK_ID, FIRST_CARD);
        // then
        verify(deckRepository).getDeckById(DECK_ID);
        verify(deckRepository, never()).updateDeck(deck);
    }

    @Test
    @DisplayName("Deck is looked up in the repository with right id")
    void getDeckSuccess() {
        // when
        deckService.getDeck(DECK_ID);
        // then
        verify(deckRepository).getDeckById(DECK_ID);
    }
    @BeforeEach
    void setUp() {
        deckRepository = Mockito.mock(DeckRepository.class);
        deckService = new DeckDomainService(deckRepository);
    }

    @AfterEach
    void tearDown() {
        deckService = null;
    }
}