package com.baeldung.lambda.shipping;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Comparator;
import java.util.UUID;

public class ShippingService {
    private SessionFactory sessionFactory;
    private ShippingDao shippingDao;

    public ShippingService(SessionFactory sessionFactory, ShippingDao shippingDao) {
        this.sessionFactory = sessionFactory;
        this.shippingDao = shippingDao;
    }

    public String createConsignment(Consignment consignment) {
        try (Session session = sessionFactory.openSession()) {
            consignment.setDelivered(false);
            consignment.setId(UUID.randomUUID().toString());
            shippingDao.save(session, consignment);
            return consignment.getId();
        }
    }

    public void addItem(String consignmentId, Item item) {
        try (Session session = sessionFactory.openSession()) {
            shippingDao.find(session, consignmentId)
              .ifPresent(consignment -> addItem(session, consignment, item));
        }
    }

    private void addItem(Session session, Consignment consignment, Item item) {
        consignment.getItems()
          .add(item);
        shippingDao.save(session, consignment);
    }

    public void checkIn(String consignmentId, Checkin checkin) {
        try (Session session = sessionFactory.openSession()) {
            shippingDao.find(session, consignmentId)
              .ifPresent(consignment -> checkIn(session, consignment, checkin));
        }
    }

    private void checkIn(Session session, Consignment consignment, Checkin checkin) {
        consignment.getCheckins().add(checkin);
        consignment.getCheckins().sort(Comparator.comparing(Checkin::getTimeStamp));
        if (checkin.getLocation().equals(consignment.getDestination())) {
            consignment.setDelivered(true);
        }
        shippingDao.save(session, consignment);
    }

    public Consignment view(String consignmentId) {
        try (Session session = sessionFactory.openSession()) {
            return shippingDao.find(session, consignmentId)
              .orElseGet(Consignment::new);
        }
    }
}
