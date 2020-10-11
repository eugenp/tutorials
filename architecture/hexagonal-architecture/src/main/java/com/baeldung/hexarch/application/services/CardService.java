package com.baeldung.hexarch.application.services;

import com.baeldung.hexarch.application.domain.Card;
import com.baeldung.hexarch.application.port.incoming.PaymentCase;
import com.baeldung.hexarch.application.port.incoming.SwipeCase;
import com.baeldung.hexarch.application.port.outgoing.ReadPort;
import com.baeldung.hexarch.application.port.outgoing.WritePort;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@AllArgsConstructor
public class CardService implements PaymentCase, SwipeCase {

    final private ReadPort readPort;
    final private WritePort writePort;

    @Override
    public void pay(Long id, BigDecimal amount) {
        Card account = readPort.load(id)
                .orElseThrow(NoSuchElementException::new);
        account.pay(amount);
        writePort.save(account);
    }

    @Override
    public boolean swipe(Long id, BigDecimal amount, boolean isCash) {
        Card account = readPort.load(id)
                .orElseThrow(NoSuchElementException::new);
        boolean hasSwiped = account.swipe(amount, isCash);
        if (hasSwiped) {
            writePort.save(account);
        }
        return hasSwiped;
    }
}
