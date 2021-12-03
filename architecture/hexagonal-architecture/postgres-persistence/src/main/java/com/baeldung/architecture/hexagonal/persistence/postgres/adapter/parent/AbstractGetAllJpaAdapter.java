package com.baeldung.architecture.hexagonal.persistence.postgres.adapter.parent;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Spliterators.spliteratorUnknownSize;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractGetAllJpaAdapter extends AbstractJpaAdapter<CrudRepository> {

    @Transactional(readOnly = true)
    protected <T> Optional<Set<T>> getAll() {

        Iterable entities = getRepository().findAll();

        Set<T> result = (Set<T>) StreamSupport.stream(spliteratorUnknownSize(entities.iterator(), Spliterator.ORDERED), true).map(getMapper()::entityToModel).collect(Collectors.toSet());

        return Optional.of(result);
    }
}
