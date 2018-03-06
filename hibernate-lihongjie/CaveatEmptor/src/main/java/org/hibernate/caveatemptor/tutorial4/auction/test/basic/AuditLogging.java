package org.hibernate.caveatemptor.tutorial4.auction.test.basic;

import org.testng.annotations.Test;
import auction.persistence.audit.*;
import auction.persistence.HibernateUtil;
import auction.dao.hibernate.UserDAOHibernate;
import auction.dao.hibernate.ItemDAOHibernate;
import auction.model.*;
import auction.test.HibernateIntegrationTest;
import org.hibernate.*;
import org.dbunit.operation.DatabaseOperation;

import java.util.*;
import java.math.BigDecimal;

/**
 * Tests the <tt>AuditLogInterceptor</tt>.
 *
 * @see AuditLogInterceptor
 * @author Christian Bauer
 */
public class AuditLogging extends HibernateIntegrationTest {

    protected void prepareSettings() {
        dataSetLocation = "auction/test/basedata.xml";
        beforeTestOperations.add(DatabaseOperation.CLEAN_INSERT);
    }

    @Test(groups = "integration-hibernate")
    public void testAuditTrail() {

        // Create interceptor for this test
        AuditLogInterceptor interceptor = new AuditLogInterceptor();

        // Open a Session with and for the Interceptor
        Session s = HibernateUtil.getSessionFactory().openSession(interceptor);
        interceptor.setSession(s);

        // Prepare the DAOs with the Session that has the interceptor enabled
        ItemDAOHibernate itemDAO = new ItemDAOHibernate();
        itemDAO.setSession(s);
        UserDAOHibernate userDAO = new UserDAOHibernate();
        userDAO.setSession(s);

        // Begin transactional unit of work
        s.beginTransaction();

        // We need a User object for the tests and logging
        User currentUser = userDAO.findById(1l, false);
        interceptor.setUserId(currentUser.getId());

        // Save an item with audit logging enabled
        Calendar inThreeDays = GregorianCalendar.getInstance();
        inThreeDays.roll(Calendar.DAY_OF_YEAR, 3);
        Item item = new Item("ONE", "Foo",
                currentUser,
                new MonetaryAmount(new BigDecimal("1.99"), Currency.getInstance(Locale.US)),
                new MonetaryAmount(new BigDecimal("50.33"), Currency.getInstance(Locale.US)),
                new Date(), inThreeDays.getTime());
        itemDAO.makePersistent(item);

        // Synchronize state to trigger interceptor
        s.getTransaction().commit();

        // Clear persistence context
        s.clear();

        // Check audit log (auto-commit mode query)
        Query queryAuditOne = s.createQuery("from AuditLogRecord lr where lr.entityId = :id");
        queryAuditOne.setParameter("id", item.getId());
        AuditLogRecord logRecordOne = (AuditLogRecord)queryAuditOne.uniqueResult();
        assert logRecordOne.userId.equals(currentUser.getId());

        // Close the persistence context
        s.close();
    }

}
