package com.baeldung.jpa.datetime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.Calendar;

public class DateTimeEntityRepository {
    private EntityManagerFactory emf = null;

    public DateTimeEntityRepository() {
        emf = Persistence.createEntityManagerFactory("java8-datetime-postgresql");
    }

    public JPA22DateTimeEntity find(Long id) {
        EntityManager entityManager = emf.createEntityManager();

        JPA22DateTimeEntity dateTimeTypes = entityManager.find(JPA22DateTimeEntity.class, id);

        entityManager.close();
        return dateTimeTypes;
    }

    public void save(Long id) {
        JPA22DateTimeEntity dateTimeTypes = new JPA22DateTimeEntity();
        dateTimeTypes.setId(id);

        //java.sql types: date/time
        dateTimeTypes.setSqlTime(Time.valueOf(LocalTime.now()));
        dateTimeTypes.setSqlDate(Date.valueOf(LocalDate.now()));
        dateTimeTypes.setSqlTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        //java.util types: date/calendar
        java.util.Date date = new java.util.Date();
        dateTimeTypes.setUtilTime(date);
        dateTimeTypes.setUtilDate(date);
        dateTimeTypes.setUtilTimestamp(date);

        //Calendar
        Calendar calendar = Calendar.getInstance();
        dateTimeTypes.setCalendarTime(calendar);
        dateTimeTypes.setCalendarDate(calendar);
        dateTimeTypes.setCalendarTimestamp(calendar);

        //java.time types
        dateTimeTypes.setLocalTime(LocalTime.now());
        dateTimeTypes.setLocalDate(LocalDate.now());
        dateTimeTypes.setLocalDateTime(LocalDateTime.now());

        //java.time types with offset
        dateTimeTypes.setOffsetTime(OffsetTime.now());
        dateTimeTypes.setOffsetDateTime(OffsetDateTime.now());

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(dateTimeTypes);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void clean() {
        emf.close();
    }
}
