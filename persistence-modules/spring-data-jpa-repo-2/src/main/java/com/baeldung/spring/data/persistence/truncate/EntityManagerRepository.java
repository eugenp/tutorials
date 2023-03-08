package com.baeldung.spring.data.persistence.truncate;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class EntityManagerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void truncateTable(String tableName) {
        String sql = "TRUNCATE TABLE " + tableName;
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }
}
