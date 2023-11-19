package com.baeldung.states;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jakarta.persistence.EntityManagerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserEntityIntegrationTest {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    void givenName_thenShouldCreateDetachedUserEntity() {
        // given
        Session session = openSession();
        UserEntity userEntity = new UserEntity("John");

        // then
        assertThat(session.contains(userEntity)).isFalse();
        session.close();
    }

    @Test
    void givenName_whenPersisted_thenShouldCreatePersistentUserEntity() {
        // given
        Session session = openSession();
        UserEntity userEntity = new UserEntity("John");

        // when
        session.persist(userEntity);

        // then
        assertThat(session.contains(userEntity)).isTrue();
        session.close();
    }

    @Test
    void givenPersistentEntity_whenSessionClosed_thenShouldDetachEntity() {
        // given
        Session session = openSession();
        UserEntity userEntity = new UserEntity("John");
        session.persist(userEntity);
        assertThat(session.contains(userEntity)).isTrue();

        // when
        session.close();

        // then
        assertThat(session.isOpen()).isFalse();
        assertThatThrownBy(() -> session.contains(userEntity));
    }

    @Test
    void givenPersistentEntity_whenAddedTransientManager_thenShouldThrowException() {
        // given
        Session session = openSession();
        Transaction transaction = session.beginTransaction();
        UserEntity userEntity = new UserEntity("John");
        session.persist(userEntity);
        UserEntity manager = new UserEntity("Adam");

        // when
        userEntity.setManager(manager);


        // then
        assertThatThrownBy(() -> {
            session.saveOrUpdate(userEntity);
            transaction.commit();
        });
        session.close();
    }

    @Test
    void givenPersistentEntity_whenAddedPersistentManager_thenShouldSave() {
        // given
        Session session = openSession();
        Transaction transaction = session.beginTransaction();
        UserEntity userEntity = new UserEntity("John");
        session.persist(userEntity);
        UserEntity manager = new UserEntity("Adam");
        session.persist(manager);

        // when
        userEntity.setManager(manager);


        // then
        session.saveOrUpdate(userEntity);
        transaction.commit();
        session.close();

        Session otherSession = openSession();
        UserEntity savedUser = otherSession.get(UserEntity.class, "John");
        assertThat(savedUser.getManager().getName()).isEqualTo("Adam");
    }

    @Test
    void givenPersistentEntityWithCascade_whenAddedTransientManager_thenShouldSave() {
        // given
        Session session = openSession();
        Transaction transaction = session.beginTransaction();
        UserEntityWithCascade userEntity = new UserEntityWithCascade("John");
        session.persist(userEntity);
        UserEntityWithCascade manager = new UserEntityWithCascade("Adam");

        // when
        userEntity.setManager(manager);


        // then
        session.saveOrUpdate(userEntity);
        transaction.commit();
        session.close();

        Session otherSession = openSession();
        UserEntityWithCascade savedUser = otherSession.get(UserEntityWithCascade.class, "John");
        assertThat(savedUser.getManager().getName()).isEqualTo("Adam");
    }


    private Session openSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).openSession();
    }
}