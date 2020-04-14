package com.baeldung.dddhexagonalapp.coreapp.repository;

import java.util.List;
import java.util.Optional;

import com.baeldung.dddhexagonalapp.coreapp.domain.CardHolder;

public interface CardHolderRepository {

    public CardHolder CreateCardHolder(CardHolder cardholder);

    public List<CardHolder> findAll();

    public Optional<CardHolder> findCardHolderById(int cardHolderId);

}
