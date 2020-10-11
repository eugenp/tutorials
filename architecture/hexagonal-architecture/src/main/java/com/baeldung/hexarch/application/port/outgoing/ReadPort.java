package com.baeldung.hexarch.application.port.outgoing;

import com.baeldung.hexarch.application.domain.Card;

import java.util.Optional;

public interface ReadPort {
    Optional<Card> load(Long id);
}