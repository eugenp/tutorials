package com.baeldung.lambda.shipping;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class ShippingDao {
    /**
     * Save a consignment to the database
     * @param consignment the consignment to save
     */
    public void save(Session session, Consignment consignment) {
        Transaction transaction = session.beginTransaction();
        session.save(consignment);
        transaction.commit();
    }

    /**
     * Find a consignment in the database by id
     */
    public Optional<Consignment> find(Session session, String id) {
        return Optional.ofNullable(session.get(Consignment.class, id));
    }
}
