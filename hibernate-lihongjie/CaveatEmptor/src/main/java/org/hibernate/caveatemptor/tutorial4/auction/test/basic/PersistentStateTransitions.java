package org.hibernate.caveatemptor.tutorial4.auction.test.basic;

import auction.model.*;
import auction.dao.*;
import auction.persistence.HibernateUtil;
import auction.exceptions.BusinessException;
import auction.exceptions.PermissionException;
import auction.test.HibernateIntegrationTest;
import org.hibernate.*;
import org.testng.annotations.*;
import org.dbunit.operation.DatabaseOperation;

import java.util.*;
import java.math.BigDecimal;

/**
 * Load and store various objects to see if they change state correctly.
 *
 * @author Christian Bauer
 */
public class PersistentStateTransitions extends HibernateIntegrationTest {

    DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);

    protected void prepareSettings() {
        dataSetLocation = "auction/test/basedata.xml";
        beforeTestOperations.add(DatabaseOperation.CLEAN_INSERT);
    }

    @Test(groups = "integration-hibernate")
    public void storeAndLoadItem() {

        // Start a unit of work
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        // Prepare the DAOs
        ItemDAO itemDAO = daoFactory.getItemDAO();
        UserDAO userDAO = daoFactory.getUserDAO();

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

        itemDAO.makePersistent(newItem);

        // End the unit of work
        HibernateUtil.getSessionFactory().getCurrentSession()
                        .getTransaction().commit();

        // Direct SQL query for database state in auto-commit mode
        StatelessSession s = HibernateUtil.getSessionFactory().openStatelessSession();
        Object[] result = (Object[])
                s.createSQLQuery("select INITIAL_PRICE ip," +
                                 "       SELLER_ID sid from ITEM where ITEM_ID = :itemid")
                  .addScalar("ip",  Hibernate.BIG_DECIMAL)
                  .addScalar("sid", Hibernate.LONG)
                  .setParameter("itemid", newItem.getId())
                  .uniqueResult();
        s.close();

        // Assert correctness of state
        assert result[0].getClass() == BigDecimal.class;
        assert result[0].equals( newItem.getInitialPrice().getValue() );
        assert result[1].equals( 1l );

    }

    @Test(groups = "integration-hibernate")
    public void manualCastOfBillingDetails() {

        // Start a unit of work
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        // Prepare the DAOs
        UserDAO userDAO = daoFactory.getUserDAO();

        User user = userDAO.findById(1l, false);

        // An (instanceof CreditCard) check to user.defaultBillingDetails would fail...
        // load() trick to retrieve the subclass instance, replacing the superclass proxy
        CreditCard cc = (CreditCard) HibernateUtil.getSessionFactory().getCurrentSession()
                .load(CreditCard.class, user.getDefaultBillingDetails().getId());
        assert cc.getType().equals(CreditCardType.MASTERCARD);

        // The previous operation produced a warning, because object identity has now been violated,
        // one database row (well, several if it's a joined hierarchy mapping) is now represented
        // by _two_ instances in-memory:
        assert user.getDefaultBillingDetails() != cc;

        // End the unit of work
        HibernateUtil.getSessionFactory().getCurrentSession()
                        .getTransaction().commit();
    }

    @Test(groups = "integration-hibernate")
    public void createShipmentWithoutAuction() {

        // Start a unit of work
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        // Prepare the DAOs
        UserDAO userDAO = daoFactory.getUserDAO();
        ShipmentDAO shipDAO = daoFactory.getShipmentDAO();

        User user1 = userDAO.findById(1l, false);
        User user2 = userDAO.findById(2l, false);

        // User 1 buys from User 2
        Shipment newShipment = new Shipment(user1.getShippingAddress(),
                                            user1, user2, 5);

        shipDAO.makePersistent(newShipment);

        // End the unit of work
        HibernateUtil.getSessionFactory().getCurrentSession()
                        .getTransaction().commit();

        // Direct SQL query for database state in auto-commit mode
        StatelessSession s = HibernateUtil.getSessionFactory().openStatelessSession();
        Object[] result = (Object[])
                s.createSQLQuery("select BUYER_ID buyerId," +
                                 "       SELLER_ID sellerId from SHIPMENT where SHIPMENT_ID = :shipId")
                  .addScalar("buyerId",  Hibernate.LONG)
                  .addScalar("sellerId", Hibernate.LONG)
                  .setParameter("shipId", newShipment.getId())
                  .uniqueResult();
        s.close();

        // Assert correctness of state
        assert result[0].equals( 1l );
        assert result[1].equals( 2l );
    }

    @Test(groups = "integration-hibernate")
    public void createShipmentForAuction() {

        // Start a unit of work
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        // Prepare the DAOs
        UserDAO userDAO = daoFactory.getUserDAO();
        ShipmentDAO shipDAO = daoFactory.getShipmentDAO();
        ItemDAO itemDAO = daoFactory.getItemDAO();

        User user1 = userDAO.findById(1l, false);
        User user2 = userDAO.findById(2l, false);
        Item item = itemDAO.findById(1l, false);

        // User 1 buys from User 2
        Shipment newShipment = new Shipment(user1.getShippingAddress(),
                                            user1, user2, 5);
        newShipment.setAuction(item);

        shipDAO.makePersistent(newShipment);

        // End the unit of work
        HibernateUtil.getSessionFactory().getCurrentSession()
                        .getTransaction().commit();

        // Direct SQL query for database state in auto-commit mode
        StatelessSession s = HibernateUtil.getSessionFactory().openStatelessSession();
        Object[] result = (Object[])
                s.createSQLQuery("select ITEM_ID itemId," +
                                 "       SHIPMENT_ID shipmentId from ITEM_SHIPMENT")
                  .addScalar("itemId",  Hibernate.LONG)
                  .addScalar("shipmentId", Hibernate.LONG)
                  .uniqueResult();
        s.close();

        // Assert correctness of state
        assert result[0].equals( 1l );
        assert result[1].equals( newShipment.getId() );
    }


    @Test(groups = "integration-hibernate")
    public void eagerFetchCategorizedItems() {

        // Start a unit of work
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        // Query for Category and all categorized Items (three tables joined)
        Query q = HibernateUtil.getSessionFactory().getCurrentSession()
                .createQuery("select c from Category as c" +
                        " left join fetch c.categorizedItems as ci" +
                        " join fetch ci.item as i");
        Collection<Category> result = new HashSet<Category>(q.list());
        assert result.size() == 2;

        // End the unit of work
        HibernateUtil.getSessionFactory().getCurrentSession()
                        .getTransaction().commit();

        // Check detached state (graph should be eager fetched)
        for (Category cat : result) {
            for (CategorizedItem categorizedItem : cat.getCategorizedItems()) {
                assert categorizedItem != null;
                assert categorizedItem.getItem() != null;
            }
        }
    }

    @Test(groups = "integration-hibernate")
    public void queryCategorizedItems() {

        // Start a unit of work
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        // Prepare the DAOs
        CategorizedItemDAO catItemDAO = daoFactory.getCategorizedItemDAO();

        CategorizedItem.Id id = new CategorizedItem.Id(1l, 1l);
        CategorizedItem catItem = catItemDAO.findById(id, false);

        // Also initializes proxy
        assert "johndoe".equals(catItem.getUsername());

        // End the unit of work
        HibernateUtil.getSessionFactory().getCurrentSession()
                        .getTransaction().commit();

    }

    @Test(groups = "integration-hibernate",
          expectedExceptions = ObjectNotFoundException.class)
    public void orphanDeletionOfCategorizedItems() {

        // Start a unit of work
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        // Prepare the DAOs
        ItemDAO itemDAO = daoFactory.getItemDAO();

        // Delete all links for item4 by clearing collection
        Item i = itemDAO.findById(1l, false);
        assert i.getCategorizedItems().size() == 2;
        i.getCategorizedItems().clear();

        // End the unit of work
        HibernateUtil.getSessionFactory().getCurrentSession()
                        .getTransaction().commit();

        // Start a unit of work
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        // Prepare the DAOs
        itemDAO = daoFactory.getItemDAO();
        CategorizedItemDAO catItemDAO = daoFactory.getCategorizedItemDAO();

        // Check deletion
        i = itemDAO.findById(1l, false);
        assert i.getCategorizedItems().size() == 0;

        CategorizedItem.Id id = new CategorizedItem.Id(1l, 1l);
        CategorizedItem catItem = catItemDAO.findById(id, false);
        catItem.getUsername(); // Force proxy initialization, throws exception
    }

    @Test(groups = "integration-hibernate",
          expectedExceptions = BusinessException.class)
    public void auctionNotActive() {

        // Start a unit of work
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        // Prepare the DAOs
        ItemDAO itemDAO = daoFactory.getItemDAO();
        UserDAO userDAO = daoFactory.getUserDAO();

        Bid currentMaxBid = itemDAO.getMaxBid(2l);
        Bid currentMinBid = itemDAO.getMinBid(2l);
        Item auction = itemDAO.findById(2l, true);

        // Fail, auction is not active yet
        BigDecimal bidAmount = new BigDecimal("333");
        MonetaryAmount newAmount = new MonetaryAmount(bidAmount, Currency.getInstance("USD"));
        auction.placeBid(userDAO.findById(1l, false),
                newAmount,
                currentMaxBid,
                currentMinBid);

        // End the unit of work
        HibernateUtil.getSessionFactory().getCurrentSession()
                        .getTransaction().commit();
    }

    @Test(groups = "integration-hibernate",
          expectedExceptions = PermissionException.class)
    public void userNotAllowedToApprove() {

        // Start a unit of work
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        // Prepare the DAOs
        ItemDAO itemDAO = daoFactory.getItemDAO();
        UserDAO userDAO = daoFactory.getUserDAO();

        // Fail, user isn't an admin
        itemDAO.findById(2l, true).approve(userDAO.findById(2l, false));

        // End the unit of work
        HibernateUtil.getSessionFactory().getCurrentSession()
                        .getTransaction().commit();
    }

    @Test(groups = "integration-hibernate")
    public void userAllowedToApprove() {

        // Start a unit of work
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        // Prepare the DAOs
        ItemDAO itemDAO = daoFactory.getItemDAO();
        UserDAO userDAO = daoFactory.getUserDAO();

        // Don't fail, user is an admin
        Item auction = itemDAO.findById(2l, true);
        auction.setPendingForApproval();
        auction.approve(userDAO.findById(1l, false));

        // End the unit of work
        HibernateUtil.getSessionFactory().getCurrentSession()
                        .getTransaction().commit();
    }

    @Test(groups = "integration-hibernate")
    public void placeBid() {

        // Start a unit of work
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        // Prepare the DAOs
        ItemDAO itemDAO = daoFactory.getItemDAO();
        UserDAO userDAO = daoFactory.getUserDAO();

        Item auction = itemDAO.findById(2l, true);
        Bid currentMaxBid = itemDAO.getMaxBid(auction.getId());
        Bid currentMinBid = itemDAO.getMinBid(auction.getId());

        // Activate
        auction.setPendingForApproval();
        auction.approve(userDAO.findById(1l, false));

        // Place a bid
        BigDecimal bidAmount = new BigDecimal("333.00");
        MonetaryAmount newAmount = new MonetaryAmount(bidAmount, Currency.getInstance("USD"));
        auction.placeBid(userDAO.findById(2l, false),
                newAmount,
                currentMaxBid,
                currentMinBid);

        // End the unit of work
        HibernateUtil.getSessionFactory().getCurrentSession()
                        .getTransaction().commit();
    }

    @Test(groups = "integration-hibernate",
          expectedExceptions = BusinessException.class)
    public void placeBidNotActive() {

        // Start a unit of work
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        // Prepare the DAOs
        ItemDAO itemDAO = daoFactory.getItemDAO();
        UserDAO userDAO = daoFactory.getUserDAO();

        Item auction = itemDAO.findById(2l, true);
        Bid currentMaxBid = itemDAO.getMaxBid(auction.getId());
        Bid currentMinBid = itemDAO.getMinBid(auction.getId());

        // Place a bid
        BigDecimal bidAmount = new BigDecimal("333.00");
        MonetaryAmount newAmount = new MonetaryAmount(bidAmount, Currency.getInstance("USD"));
        auction.placeBid(userDAO.findById(2l, false),
                newAmount,
                currentMaxBid,
                currentMinBid);

        // End the unit of work
        HibernateUtil.getSessionFactory().getCurrentSession()
                        .getTransaction().commit();
    }

    @Test(groups = "integration-hibernate",
          expectedExceptions = BusinessException.class)
    public void placeBidTooLow() {

        // Start a unit of work
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        // Prepare the DAOs
        ItemDAO itemDAO = daoFactory.getItemDAO();
        UserDAO userDAO = daoFactory.getUserDAO();

        Item auction = itemDAO.findById(2l, true);
        Bid currentMaxBid = itemDAO.getMaxBid(auction.getId());
        Bid currentMinBid = itemDAO.getMinBid(auction.getId());

        // Activate
        auction.setPendingForApproval();
        auction.approve(userDAO.findById(1l, false));

        // Place a bid
        BigDecimal bidAmount = new BigDecimal("100.00");
        MonetaryAmount newAmount = new MonetaryAmount(bidAmount, Currency.getInstance("USD"));
        auction.placeBid(userDAO.findById(2l, false),
                newAmount,
                currentMaxBid,
                currentMinBid);

        // End the unit of work
        HibernateUtil.getSessionFactory().getCurrentSession()
                        .getTransaction().commit();
    }

}
