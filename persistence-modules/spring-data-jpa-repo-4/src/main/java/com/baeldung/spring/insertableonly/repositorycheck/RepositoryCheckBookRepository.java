package com.baeldung.spring.insertableonly.repositorycheck;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryCheckBookRepository extends JpaRepository<RepositoryCheckBook, Long> {

    default <S extends RepositoryCheckBook> S persist(S entity) {
        if (entity.getId() == null) {
            return save(entity);
        }
        return entity;
    }
}
