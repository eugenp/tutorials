package org.hibernate.caveatemptor.tutorial3.auction.test.basic;

import org.dbunit.operation.DatabaseOperation;

import org.testng.annotations.*;

import java.util.*;
import java.math.BigDecimal;
import java.math.BigInteger;

import auction.dao.ItemDAO;
import auction.dao.UserDAO;
import auction.dao.ejb3.GenericEJB3DAO;
import auction.dao.ejb3.ItemDAOBean;
import auction.dao.ejb3.UserDAOBean;
import auction.model.User;
import auction.model.Item;
import auction.model.MonetaryAmount;
import auction.test.EJB3IntegrationTest;

import javax.persistence.EntityManager;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.ColumnResult;

// For validation query in native SQL
@SqlResultSetMapping(
    name = "PriceSellerResult",
    columns = {
        @ColumnResult(name = "IP"),
        @ColumnResult(name = "SID")

    }
)

public class PersistentStateTransitions extends EJB3IntegrationTest {

    protected void prepareSettings() {
        dataSetLocation = "auction/test/basedata.xml";
        beforeTestOperations.add(DatabaseOperation.CLEAN_INSERT);
    }

    /**
     * This test doesn't really need the EJB container, it starts a UserTransaction
     * manually and instantiates DAOs directly. However, the datasource is still
     * managed by the container. The EntityManagerFactory is also handled by the
     * container.
     */
    @Test(groups = "integration-persistence")
    public void withoutEJBContainer() throws Exception {

        // Start a unit of work (manually, no container)
        getUserTransaction().begin();
        EntityManager em = getEntityManagerFactory().createEntityManager();

        // Prepare the DAOs (manually, no Seam)
        ItemDAO itemDAO = new ItemDAOBean();
        ((GenericEJB3DAO) itemDAO).setEntityManager(em);

        UserDAO userDAO = new UserDAOBean();
        ((GenericEJB3DAO) userDAO).setEntityManager(em);

        // Prepare a user object
        User user = userDAO.findById(1l, false);

        // Make a new auction item persistent
        Calendar startDate = GregorianCalendar.getInstance();
        Calendar endDate = GregorianCalendar.getInstance();
        endDate.add(Calendar.DAY_OF_YEAR, 3);

        MonetaryAmount initialPrice =
            new MonetaryAmount(new BigDecimal(123), Currency.getInstance("USD"));
        MonetaryAmount reservePrice =
            new MonetaryAmount(new BigDecimal(333), Currency.getInstance("USD"));

        Item newItem =
            new Item( "Testitem", "Test Description", user,
                      initialPrice, reservePrice,
                      startDate.getTime(), endDate.getTime() );

        // Don't forget to take the return value, this is basically a merge()
        newItem = itemDAO.makePersistent(newItem);

        // End the unit of work
        getUserTransaction().commit();
        em.close();


        // Direct SQL query for database state in auto-commit mode
        em = getEntityManagerFactory().createEntityManager();
        Object[] result = (Object[])
                em.createNativeQuery(
                        "select INITIAL_PRICE as IP," +
                        "       SELLER_ID as SID from ITEM where ITEM_ID = :itemId")
                        .setParameter("itemId", newItem.getId())
                        .getSingleResult();
        em.close();


        // Assert correctness of state
        assert result[0].getClass() == BigDecimal.class;
        assert result[0].equals(newItem.getInitialPrice().getValue());
        // The SQL resultset mapping returns a BigInteger
        assert result[1].equals(new BigInteger("1"));

    }

    /**
     * This test looks up the DAOs as managed EJBs. The DAOs will have the
     * current persistence context injected automatically, the persistence
     * context is scoped and bound to the JTA transaction.
     */
    @Test(groups = "integration-persistence")
    public void withEJBContainer() throws Exception {

        // Start a unit of work (manually, no Seam)
        getUserTransaction().begin();

        // Prepare the DAOs (manually, no Seam)
        ItemDAO itemDAO = lookupLocalBean(ItemDAO.class, "ItemDAOBean");
        UserDAO userDAO = lookupLocalBean(UserDAO.class, "UserDAOBean");

        // Prepare a user object
        User user = userDAO.findById(1l, false);

        // Make a new auction item persistent
        Calendar startDate = GregorianCalendar.getInstance();
        Calendar endDate = GregorianCalendar.getInstance();
        endDate.add(Calendar.DAY_OF_YEAR, 3);

        MonetaryAmount initialPrice =
            new MonetaryAmount(new BigDecimal(123), Currency.getInstance("USD"));
        MonetaryAmount reservePrice =
            new MonetaryAmount(new BigDecimal(333), Currency.getInstance("USD"));

        Item newItem =
            new Item( "Testitem", "Test Description", user,
                      initialPrice, reservePrice,
                      startDate.getTime(), endDate.getTime() );

        // Don't forget to take the return value, this is basically a merge()
        newItem = itemDAO.makePersistent(newItem);

        // End the unit of work
        getUserTransaction().commit();


        // Direct SQL query for database state in auto-commit mode
        EntityManager em = getEntityManagerFactory().createEntityManager();
        Object[] result = (Object[])
                em.createNativeQuery(
                        "select INITIAL_PRICE as IP," +
                        "       SELLER_ID as SID from ITEM where ITEM_ID = :itemId")
                        .setParameter("itemId", newItem.getId())
                        .getSingleResult();
        em.close();


        // Assert correctness of state
        assert result[0].getClass() == BigDecimal.class;
        assert result[0].equals(newItem.getInitialPrice().getValue());
        // The SQL resultset mapping returns a BigInteger
        assert result[1].equals(new BigInteger("1"));

    }


}
