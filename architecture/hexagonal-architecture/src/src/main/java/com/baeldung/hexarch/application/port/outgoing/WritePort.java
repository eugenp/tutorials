package com.baeldung.hexarch.application.port.outgoing;

import com.baeldung.hexarch.adapters.persistence.entity.CardEntity;

public interface WritePort {
    void save(CardEntity card);
}