package com.baeldung.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class EMFService {
    private static final EntityManagerFactory emfInstance = Persistence
            .createEntityManagerFactory("transactions-optional");

    private EMFService() {
    }

    public static EntityManagerFactory get() {
        return emfInstance;
    }
}