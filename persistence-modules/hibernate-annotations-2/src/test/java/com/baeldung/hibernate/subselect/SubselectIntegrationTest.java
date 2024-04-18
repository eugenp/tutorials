package com.baeldung.hibernate.subselect;

import com.baeldung.hibernate.HibernateAnnotationUtil;
import jakarta.persistence.criteria.Root;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.hibernate.Session;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SubselectIntegrationTest {

  @BeforeAll
  static void setUp() {
    Session currentSession = HibernateAnnotationUtil.getSessionFactory().getCurrentSession();
    currentSession.beginTransaction();

    currentSession.doWork(it -> {
      Liquibase liquibase;
      try {
        liquibase = new Liquibase(
            "migrations/master.xml",
            new ClassLoaderResourceAccessor(),
            DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(it))
        );
        liquibase.update(new Contexts(), new LabelExpression(),true);
      } catch (LiquibaseException e) {
        throw new RuntimeException(e);
      }
    });

    currentSession.getTransaction().commit();
  }

  @Test
  void givenEntityMarkedWithSubselect_whenSelectingRuntimeConfigByKey_thenSelectedSuccessfully() {
    String key = "splitting.enabled";
    Session entityManager = HibernateAnnotationUtil.getSessionFactory().getCurrentSession();

    entityManager.beginTransaction();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

    CriteriaQuery<RuntimeConfiguration> query = criteriaBuilder.createQuery(RuntimeConfiguration.class);

    Root<RuntimeConfiguration> root = query.from(RuntimeConfiguration.class);

    RuntimeConfiguration configurationParameter = entityManager.createQuery(
        query.select(root).where(criteriaBuilder.equal(root.get("key"), key))
    ).getSingleResult();

    entityManager.getTransaction().commit();

    Assertions.assertThat(configurationParameter.getValue()).isEqualTo("true");
  }
}

