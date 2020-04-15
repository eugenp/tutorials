package com.baeldung.dddhexagonalapp.coreapp.repository;

import java.util.List;
import java.util.Optional;

import com.baeldung.dddhexagonalapp.coreapp.domain.CardHolder;

public interface CardHolderRepository {

    CardHolder CreateCardHolder(CardHolder cardholder);

    List<CardHolder> findAll();

    Optional<CardHolder> findCardHolderById(int cardHolderId);

}
