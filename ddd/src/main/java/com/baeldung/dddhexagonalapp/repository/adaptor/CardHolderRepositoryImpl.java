package com.baeldung.dddhexagonalapp.repository.adaptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import com.baeldung.dddhexagonalapp.coreapp.domain.CardHolder;
import com.baeldung.dddhexagonalapp.coreapp.repository.CardHolderRepository;

public class CardHolderRepositoryImpl implements CardHolderRepository {

    private Map<Integer, CardHolder> dataStore = new HashMap<>();

    private static AtomicInteger atomicInt = new AtomicInteger(0);

    public static int getPrimaryKey() {
        return atomicInt.incrementAndGet();
    }

    public CardHolder CreateCardHolder(CardHolder cardholder) {

        cardholder.setCardHolderId(getPrimaryKey());
        cardholder.setCreditCardLimit(5000);
        cardholder.setStatus("PREMIUM");

        dataStore.putIfAbsent(cardholder.getCardHolderId(), cardholder);

        return cardholder;
    }

    @Override
    public List<CardHolder> findAll() {
        List<CardHolder> all = new ArrayList<>();
        dataStore.values()
            .stream()
            .forEach(c -> all.add(c));
        return all;
    }

    @Override
    public Optional<CardHolder> findCardHolderById(int cardHolderId) {

        Optional<CardHolder> opt = Optional.empty();

        if (this.dataStore.containsKey(cardHolderId)) {
            opt = Optional.of(dataStore.get(cardHolderId));
        }
        return opt;
    }

}
