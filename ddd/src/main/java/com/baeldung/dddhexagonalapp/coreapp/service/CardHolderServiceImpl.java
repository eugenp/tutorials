package com.baeldung.dddhexagonalapp.coreapp.service;

import java.util.List;
import java.util.Optional;

import com.baeldung.dddhexagonalapp.coreapp.repository.CardHolderRepository;
import com.baeldung.dddhexagonalapp.coreapp.domain.CardHolder;
import com.google.inject.Inject;

public class CardHolderServiceImpl implements CardHolderService {

    private CardHolderRepository repo;

    @Inject
    public CardHolderServiceImpl(CardHolderRepository respository) {
        repo = respository;
    }

    public CardHolder registerCardHolder(CardHolder cardHolder) {

        return repo.CreateCardHolder(cardHolder);

    }

    public List<CardHolder> getAllCardHolders() {
        return repo.findAll();
    }

    public Optional<CardHolder> findCardHolderById(int cardHolderId) {
        return repo.findCardHolderById(cardHolderId);
    }

}
