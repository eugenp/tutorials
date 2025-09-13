package com.baeldung.listvsset.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUtil {

    public static final String TRUNCATE_QUERY_FORMAT = "TRUNCATE TABLE %s";
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void truncateAllTables() {
        switchForeignKeysOff();
        entityManager.createNativeQuery("SHOW TABLES", String.class)
          .getResultList()
          .forEach(this::truncateTable);
        switchForeignKeysOn();
    }

    @Transactional
    public <T> void saveAll(List<T> entities) {
        entities.forEach(s -> entityManager.persist(s));
    }

    @Transactional
    public <T> void mergeAll(List<T> entities) {
        entities.forEach(s -> entityManager.merge(s));
    }

    private int truncateTable(Object s) {
        return entityManager
          .createNativeQuery(TRUNCATE_QUERY_FORMAT.formatted(s)).executeUpdate();
    }

    private void switchForeignKeysOn() {
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    private void switchForeignKeysOff() {
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
    }
}
