package com.baeldung.hibernate.find;

import jakarta.data.repository.Repository;
import jakarta.persistence.EntityManager;

@Repository
public interface AuthorRepository {
    EntityManager entityManager();
}
