package com.baeldung.dddhexagonalapp.coreapp.repository;

import java.util.List;
import java.util.Optional;

import com.baeldung.dddhexagonalapp.coreapp.domain.CardHolder;
import com.baeldung.dddhexagonalapp.coreapp.exception.CardHolderNotFoundException;

public interface CardHolderRepository {

    public CardHolder CreateCardHolder(CardHolder cardholder);

    public CardHolder UpdateCardHolder(CardHolder cardHolder) throws CardHolderNotFoundException;

    public List<CardHolder> findAll();

    public Optional<CardHolder> findCardHolderById(int cardHolderId);

}
