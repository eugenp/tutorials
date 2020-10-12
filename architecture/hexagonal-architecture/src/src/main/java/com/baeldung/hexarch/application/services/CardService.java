package com.baeldung.hexarch.application.services;

import com.baeldung.hexarch.adapters.persistence.entity.CardEntity;
import com.baeldung.hexarch.application.domain.Card;
import com.baeldung.hexarch.application.port.incoming.PaymentCase;
import com.baeldung.hexarch.application.port.incoming.SwipeCase;
import com.baeldung.hexarch.application.port.outgoing.ReadPort;
import com.baeldung.hexarch.application.port.outgoing.WritePort;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class CardService implements PaymentCase, SwipeCase {

    final private ReadPort readPort;
    final private WritePort writePort;

    @Override
    public void pay(Long id, BigDecimal amount) {
        CardEntity cardEntity = readPort.load(id)
                .orElseThrow(NoSuchElementException::new);
        Card card = cardEntity.entityToModel();
        card.pay(amount);
        writePort.save(card.convertModelToEntity());
    }

    @Override
    public boolean swipe(Long id, BigDecimal amount, boolean isCash) {
        CardEntity cardEntity = readPort.load(id)
                .orElseThrow(NoSuchElementException::new);
        Card card = cardEntity.entityToModel();
        boolean hasSwiped = card.swipe(amount, isCash);
        if (hasSwiped) {
            writePort.save(card.convertModelToEntity());
        }
        return hasSwiped;
    }
}
