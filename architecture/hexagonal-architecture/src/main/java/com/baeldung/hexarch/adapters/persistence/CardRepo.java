package com.baeldung.hexarch.adapters.persistence;

import com.baeldung.hexarch.application.domain.Card;
import org.springframework.data.repository.CrudRepository;

public interface CardRepo extends CrudRepository<Card, Long> { }
