package com.baeldung.hexarch.application.port.outgoing;

import com.baeldung.hexarch.application.domain.Card;

public interface WritePort {
    void save(Card card);
}