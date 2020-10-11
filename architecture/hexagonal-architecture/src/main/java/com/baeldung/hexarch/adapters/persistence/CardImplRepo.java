package com.baeldung.hexarch.adapters.persistence;

import com.baeldung.hexarch.application.domain.Card;
import com.baeldung.hexarch.application.port.outgoing.ReadPort;
import com.baeldung.hexarch.application.port.outgoing.WritePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CardImplRepo implements ReadPort, WritePort {

    final private CardRepo repository;

    @Override
    public Optional<Card> load(Long id) {
        return repository.findById(id);
    }

    @Override
    public void save(Card card) {
        repository.save(card);
    }
}
