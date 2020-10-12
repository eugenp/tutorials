package com.baeldung.hexarch.application.port.outgoing;

import com.baeldung.hexarch.adapters.persistence.entity.CardEntity;
import com.baeldung.hexarch.application.domain.Card;

import java.util.Optional;

public interface ReadPort {
    Optional<CardEntity> load(Long id);
}