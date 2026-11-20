package com.baeldung.concreteproxy;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = Application.class)
@TestPropertySource(properties = {
    "spring.sql.init.mode=never"
})
class ConcreteProxyIntegrationTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    void whenAccessingLazyLoadedProxy_thenConcreteTypeIsResolved() {
        Long wizardId;
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();

            Gryffindor gryffindor = new Gryffindor("Godric Gryffindor", "Scarlet and Gold", true);
            session.persist(gryffindor);

            Wizard wizard = new Wizard("Neville Longbottom", gryffindor);
            session.persist(wizard);

            wizardId = wizard.getId();
            session.getTransaction().commit();
        }

        try (Session session = sessionFactory.openSession()) {
            Wizard wizard = session.find(Wizard.class, wizardId);
            HogwartsHouse hogwartsHouse = wizard.getHogwartsHouse();

            assertThat(hogwartsHouse)
                .isInstanceOf(HogwartsHouse.class);
            assertThat(hogwartsHouse.getId())
                .isNotNull()
                .isPositive();

            assertThat(hogwartsHouse)
                .isInstanceOf(Gryffindor.class);
            assertThat(((Gryffindor) hogwartsHouse).getHasSummonedSword())
                .isTrue();
        }
    }
}