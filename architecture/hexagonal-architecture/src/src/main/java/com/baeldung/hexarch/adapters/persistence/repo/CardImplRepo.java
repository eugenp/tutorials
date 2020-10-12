package com.baeldung.hexarch.adapters.persistence.repo;

import com.baeldung.hexarch.adapters.persistence.entity.CardEntity;
import com.baeldung.hexarch.application.domain.Card;
import com.baeldung.hexarch.application.port.outgoing.ReadPort;
import com.baeldung.hexarch.application.port.outgoing.WritePort;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CardImplRepo implements ReadPort, WritePort {

    final private CardRepo repository;

    @Override
    public Optional<CardEntity> load(Long id) {
        return repository.findById(id);
    }

    @Override
    public void save(CardEntity card) {
        repository.save(card);
    }
}
