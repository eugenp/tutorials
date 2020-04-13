package com.baeldung.dddhexagonalapp.coreapp.service;

import java.util.List;
import java.util.Optional;

import com.baeldung.dddhexagonalapp.coreapp.exception.CardHolderNotFoundException;
import com.baeldung.dddhexagonalapp.coreapp.repository.CardHolderRepository;
import com.baeldung.dddhexagonalapp.coreapp.domain.CardCategory;
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

    public CardHolder upgradeCardHolder(CardHolder cardHolder) throws CardHolderNotFoundException {

        if (cardHolder.getStatus() == CardCategory.BUDGET) {
            cardHolder.setStatus(CardCategory.PREMIUM);
            cardHolder.setCreditCardLimit(15000);
        } else if (cardHolder.getStatus() == CardCategory.PREMIUM) {
            cardHolder.setStatus(CardCategory.EXCLUSIVE);
            cardHolder.setCreditCardLimit(30000);
        } else if (cardHolder.getStatus() == CardCategory.EXCLUSIVE) {
            cardHolder.setStatus(CardCategory.EXCLUSIVE);
            cardHolder.setCreditCardLimit(30000);
        }

        return repo.UpdateCardHolder(cardHolder);

    }

    public CardHolder downgradeCardHolder(CardHolder cardHolder) throws CardHolderNotFoundException {

        if (cardHolder.getStatus() == CardCategory.BUDGET) {
            cardHolder.setStatus(CardCategory.BUDGET);
            cardHolder.setCreditCardLimit(5000);
        } else if (cardHolder.getStatus() == CardCategory.PREMIUM) {
            cardHolder.setStatus(CardCategory.BUDGET);
            cardHolder.setCreditCardLimit(5000);
        } else if (cardHolder.getStatus() == CardCategory.EXCLUSIVE) {
            cardHolder.setStatus(CardCategory.PREMIUM);
            cardHolder.setCreditCardLimit(15000);
        }

        return repo.UpdateCardHolder(cardHolder);

    }

    public List<CardHolder> getAllCardHolders() {
        return repo.findAll();
    }

    public Optional<CardHolder> findCardHolderById(int cardHolderId) {
        return repo.findCardHolderById(cardHolderId);
    }

}
