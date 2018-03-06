package org.hibernate.caveatemptor.tutorial4.auction.test.basic;

import auction.dao.DAOFactory;
import auction.persistence.HibernateUtil;
import auction.model.*;
import auction.test.HibernateIntegrationTest;
import org.hibernate.*;
import org.dbunit.operation.DatabaseOperation;
import org.testng.annotations.Test;

/**
 * Concurrency tests.
 *
 * @author Christian Bauer
 */
public class Locking extends HibernateIntegrationTest {

    DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);

    protected void prepareSettings() {
        dataSetLocation = "auction/test/basedata.xml";
        beforeTestOperations.add(DatabaseOperation.CLEAN_INSERT);
    }

    @Test(groups = "integration-hibernate")
    public void nonRepeatableRead() {

        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();

        Item i = (Item)s.get(Item.class, 1l);
        // Would produce repeatable read if supported by DBMS, but also result in dead lock
        // in this test (s2 waits for this lock to be released...):
        // s.lock(i, LockMode.UPGRADE);

        // Now modify the item in a concurrent transaction
        Session s2 = HibernateUtil.getSessionFactory().openSession();
        Transaction tx2 = s2.beginTransaction();

        Item item2 = (Item)s2.get(Item.class, 1l);
        item2.setDescription("NEW DESCRIPTION");

        tx2.commit();
        s2.close();

        // This read returns different data than already in the Session
        String newDescription = (String)
                s.createQuery("select i.description from Item i where i.id = :itemid")
                  .setParameter("itemid", 1l)
                  .uniqueResult();

        assert !i.getDescription().equals(newDescription);

        tx.commit();
        s.close();
    }

    @Test(groups = "integration-hibernate")
    public void forcedVersionUpdate() {

        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();

        User u = (User)s.get(User.class, 1l);

        int oldVersion = u.getVersion();

        // Force a version increment
        s.lock(u, LockMode.FORCE);

        // Modify billing details
        u.getDefaultBillingDetails().setOwner("JD");

        tx.commit();
        s.close();

        int newVersion = u.getVersion();
        assert newVersion != oldVersion;
    }

}
