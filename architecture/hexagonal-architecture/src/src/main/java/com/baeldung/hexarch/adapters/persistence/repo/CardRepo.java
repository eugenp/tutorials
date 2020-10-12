package com.baeldung.hexarch.adapters.persistence.repo;

import com.baeldung.hexarch.adapters.persistence.entity.CardEntity;
import com.baeldung.hexarch.application.domain.Card;
import org.springframework.data.repository.CrudRepository;

public interface CardRepo extends CrudRepository<CardEntity, Long> { }
