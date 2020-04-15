package com.baeldung.dddhexagonalapp.coreapp.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.baeldung.dddhexagonalapp.coreapp.repository.CardHolderRepository;
import com.baeldung.dddhexagonalapp.coreapp.domain.CardHolder;
import com.baeldung.dddhexagonalapp.repository.adaptor.CardHolderRepositoryImpl;

public class CardHolderServiceImpl implements CardHolderService {

    private CardHolderRepository repo = new CardHolderRepositoryImpl();

    @Override
    public CardHolder registerCardHolder(CardHolder cardHolder) {

        Random random = new Random();
        String creditCardNumber = String.format("%04d", random.nextInt(10000)) + " " + String.format("%04d", random.nextInt(10000)) + " " + String.format("%04d", random.nextInt(10000)) + " " + String.format("%04d", random.nextInt(10000));
        cardHolder.setCreditCardNumber(creditCardNumber);

        return repo.CreateCardHolder(cardHolder);

    }

    @Override
    public List<CardHolder> getAllCardHolders() {
        return repo.findAll();
    }

    @Override
    public Optional<CardHolder> findCardHolderById(int cardHolderId) {
        return repo.findCardHolderById(cardHolderId);
    }

}
