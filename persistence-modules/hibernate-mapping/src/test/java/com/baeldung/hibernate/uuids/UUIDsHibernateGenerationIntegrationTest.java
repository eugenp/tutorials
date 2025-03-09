package com.baeldung.hibernate.uuids;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.hibernate.manytomany.HibernateUtil;

public class UUIDsHibernateGenerationIntegrationTest {

    private SessionFactory sessionFactory;

    private Session session;

    @Before
    public void setUp() throws IOException {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
    }

    @Test
    public void whenGeneratingUUIDUsingNewJPAGenerationType_thenHibernateGeneratedUUID() throws IOException {
        Reservation reservation = new Reservation();
        reservation.setStatus("created");
        reservation.setNumber("12345");
        UUID saved = (UUID) session.save(reservation);
        Assertions.assertThat(saved).isNotNull();
    }

    @Test
    public void whenGeneratingUUIDUsingNewJPAGenerationType_thenHibernateGeneratedUUIDOfVersion4() throws IOException {
        Reservation reservation = new Reservation();
        reservation.setStatus("new");
        reservation.setNumber("012");
        UUID saved = (UUID) session.save(reservation);
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.version()).isEqualTo(4);
    }

    @Test
    public void whenGeneratingUUIDUsingGenericConverter_thenAlsoGetUUIDGeneratedVersion4() throws IOException {
        Sale sale = new Sale();
        sale.setCompleted(true);
        UUID saved = (UUID) session.save(sale);
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.version()).isEqualTo(4);
    }

    @Test
    public void whenGeneratingTimeBasedUUID_thenUUIDGeneratedVersion1() throws IOException {
        WebSiteUser user = new WebSiteUser();
        user.setRegistrationDate(LocalDate.now());
        UUID saved = (UUID) session.save(user);
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.version()).isEqualTo(1);
    }

    @Test
    public void whenGeneratingUUIDAsString_thenUUIDGeneratedVersion1() throws IOException {
        Element element = new Element();
        element.setName("a");
        String saved = (String) session.save(element);
        Assertions.assertThat(saved).isNotEmpty();
        Assertions.assertThat(UUID.fromString(saved).version()).isEqualTo(4);
    }
}