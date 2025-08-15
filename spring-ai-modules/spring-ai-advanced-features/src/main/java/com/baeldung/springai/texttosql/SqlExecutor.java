package com.baeldung.springai.texttosql;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SqlExecutor {

    private final EntityManager entityManager;

    SqlExecutor(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    List<?> execute(String query) {
        List<?> result = entityManager
            .createNativeQuery(query)
            .getResultList();
        if (result.isEmpty()) {
            throw new EmptyResultException("No results found for the provided query.");
        }
        return result;
    }

}