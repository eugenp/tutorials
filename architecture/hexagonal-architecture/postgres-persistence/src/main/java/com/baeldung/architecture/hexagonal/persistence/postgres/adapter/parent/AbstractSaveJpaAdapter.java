package com.baeldung.architecture.hexagonal.persistence.postgres.adapter.parent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

@SuppressWarnings("unchecked")
@Slf4j
public abstract class AbstractSaveJpaAdapter extends AbstractJpaAdapter<CrudRepository> {

    @Transactional
    protected <T, S> Optional<T> save(S entityToSave) {
        try {
            return (Optional<T>) ofNullable(getMapper().entityToModel(getRepository().save(entityToSave)));
        } catch (Exception e) {
            log.error(e.getMessage());
            return empty();
        }
    }
}
