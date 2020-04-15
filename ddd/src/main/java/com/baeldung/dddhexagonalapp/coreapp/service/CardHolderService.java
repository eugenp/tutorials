package com.baeldung.dddhexagonalapp.coreapp.service;

import com.baeldung.dddhexagonalapp.coreapp.domain.CardHolder;

import java.util.List;
import java.util.Optional;

public interface CardHolderService {
    CardHolder registerCardHolder(CardHolder cardHolder);

    List<CardHolder> getAllCardHolders();

    Optional<CardHolder> findCardHolderById(int cardHolderId);
}
