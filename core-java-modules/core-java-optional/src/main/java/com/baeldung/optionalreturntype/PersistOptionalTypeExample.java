package com.baeldung.optionalreturntype;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistOptionalTypeExample {
    static String persistenceUnit = "com.baeldung.optionalreturntype";
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);

    static EntityManager entityManager = emf.createEntityManager();

    // to run this app, uncomment the follow line in META-INF/persistence.xml
    // <class>com.baeldung.optionalreturntype.UserOptionalField</class>
    public static void main(String[] args) {
        UserOptionalField user1 = new UserOptionalField();
        user1.setUserId(1l);
        user1.setFirstName(Optional.of("Bael Dung"));
        entityManager.persist(user1);

        UserOptional user2 = entityManager.find(UserOptional.class, 1l);
        System.out.print("User2.firstName:" + user2.getFirstName());
    }
}
