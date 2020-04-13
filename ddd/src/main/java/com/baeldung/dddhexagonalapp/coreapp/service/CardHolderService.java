package com.baeldung.dddhexagonalapp.coreapp.service;

import java.util.List;
import java.util.Optional;

import com.baeldung.dddhexagonalapp.coreapp.exception.CardHolderNotFoundException;
import com.baeldung.dddhexagonalapp.coreapp.domain.CardHolder;

public interface CardHolderService {

    public CardHolder registerCardHolder(CardHolder cardHolder);

    public CardHolder upgradeCardHolder(CardHolder cardHolder) throws CardHolderNotFoundException;

    public CardHolder downgradeCardHolder(CardHolder cardHolder) throws CardHolderNotFoundException;

    public List<CardHolder> getAllCardHolders();

    public Optional<CardHolder> findCardHolderById(int cardHolderId);

}
