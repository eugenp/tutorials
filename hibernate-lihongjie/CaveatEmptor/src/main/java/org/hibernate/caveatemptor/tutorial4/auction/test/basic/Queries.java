package org.hibernate.caveatemptor.tutorial4.auction.test.basic;

import auction.dao.*;
import auction.persistence.HibernateUtil;
import auction.persistence.StringEnumUserType;
import auction.model.*;
import auction.test.HibernateIntegrationTest;
import org.hibernate.*;
import org.hibernate.type.Type;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.criterion.*;
import org.dbunit.operation.DatabaseOperation;
import org.testng.annotations.Test;

import java.util.*;
import java.math.BigDecimal;

/**
 * Some Criteria and SQL queries.
 *
 * @author Christian Bauer
 */
public class Queries extends HibernateIntegrationTest {

    DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);

    protected void prepareSettings() {
        dataSetLocation = "auction/test/basedata.xml";
        beforeTestOperations.add(DatabaseOperation.CLEAN_INSERT);
    }

    @Test(groups = "integration-hibernate")
    public void nativeSQLQueries() {

        // Start a unit of work
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        List result;

        // Fully automatic resultset marshalling
        result = session.createSQLQuery("select * from CATEGORY")
                         .addEntity(Category.class)
                         .list();
        assert result.size() == 2;

        // Using aliases
        /* TODO: This doesn't work because of the <join many-to-one> in Item.hbm.xml
        result = session.createSQLQuery("select {i.*}, {ib.*} from ITEM i" +
                                        " join USERS u on i.SELLER_ID = u.USER_ID" +
                                        " left join ITEM_BUYER ib on ib.ITEM_ID = i.ITEM_ID " +
                                        " where u.USERNAME = :uname")
                         .addEntity("i", Item.class)
                         .addJoin("ib", "i.buyer")
                         .setParameter("uname", "johndoe")
                         .list();
        assert result.size() == 2;
        -->

        /* TODO: This is tricky, don't know how to correctly fetch a joined component, HHH-2225
        result = session.createSQLQuery("select {i.*}, {u.*}, {ba.*} from ITEM i" +
                                        " join USERS u on i.SELLER_ID = u.USER_ID" +
                                        " left join BILLING_ADDRESS ba on u.USER_ID = ba.USER_ID" +
                                        " where u.USERNAME = :uname")
                         .addEntity("i", Item.class)
                         .addJoin("u", "i.seller")
                         .addJoin("ba", "u.billingAddress")
                         .setParameter("uname", "johndoe")
                         .list();
        assert result.size() == 2;
        */

        // Just return an Object[]
        result = session.createSQLQuery("select * from ITEM").list();
        assert result.size() == 2;
        assert result.get(0) instanceof Object[];

        // Return a single scalar value
        result = session.createSQLQuery("select u.FIRSTNAME as fname from USERS u")
                         .addScalar("fname")
                         .list();
        assert result.size() == 2;

        // Returning a scalar custom Hibernate value type
        Properties params = new Properties();
        params.put("enumClassname", "auction.model.Rating");
        result = session.createSQLQuery("select c.RATING as rating from COMMENT c where c.FROM_USER_ID = :uid")
                         .addScalar("rating", Hibernate.custom(StringEnumUserType.class, params))
                         .setParameter("uid", 1l)
                         .list();
        assert result.size() == 1;
        assert result.get(0) instanceof Rating;

        // Mixing scalar results and entities
        /* TODO: This doesn't work because of the <join many-to-one> in Item.hbm.xml
        result = session.createSQLQuery("select {i.*}, u.FIRSTNAME as fname from ITEM i" +
                                        " join USERS u on i.SELLER_ID = u.USER_ID" +
                                        " where u.USERNAME = :uname")
                         .addEntity("i", Item.class)
                         .addScalar("fname")
                         .setParameter("uname", "johndoe")
                         .list();
        assert result.size() == 2;
        assert result.get(0) instanceof Object[];
        */

        // End the unit of work
        HibernateUtil.getSessionFactory().getCurrentSession()
                        .getTransaction().commit();
    }

    @Test(groups = "integration-hibernate")
    public void basicCriteriaQueries() {

        // Start a unit of work
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Criteria crit;
        List result;

        // All users with email address johndoe@mail.tld
        crit = session.createCriteria(User.class)
                        .add( Property.forName("email").eq("jd@mail.tld") );
        result = crit.list();
        assert result.size() == 1;

        // The same...
        crit = session.createCriteria(User.class)
                        .add(Restrictions.eq("email", "jd@mail.tld"));
        result = crit.list();
        assert result.size() == 1;

        // Restriction by component property with dot notation
        crit = session.createCriteria(User.class)
                        .add( Restrictions.eq("homeAddress.street", "Foostreet"));
        result = crit.list();
        assert result.size() == 2;

        // Some string matching examples
        crit = session.createCriteria(User.class)
                        .add( Restrictions.like("firstname", "J%") );
        result = crit.list();
        assert result.size() == 1;

        crit = session.createCriteria(User.class)
                        .add( Restrictions.like("firstname", "J", MatchMode.START) );
        result = crit.list();
        assert result.size() == 1;

        // Find all auctions with at least 2 bids
        crit = session.createCriteria(Item.class)
                        .add( Restrictions.sizeGt("bids", 1));
        result = crit.list();
        assert result.size() == 1;

        // Disjunction examples
        String[] emails = new String[]{"jd@mail.tld"};
        crit = session.createCriteria(User.class)
                    .add(
                        Restrictions.or(
                            Restrictions.and(
                                Restrictions.like("firstname", "J%"),
                                Restrictions.like("lastname", "D%")
                            ),
                            Restrictions.in("email", emails)
                        )
                    );
        result = crit.list();
        assert result.size() == 1;

        crit = session.createCriteria(User.class)
                .add( Restrictions.disjunction()
                    .add( Restrictions.conjunction()
                        .add( Restrictions.like("firstname", "J%") )
                        .add( Restrictions.like("lastname", "D%") )
                    )
                    .add( Restrictions.in("email", emails) )
                );
        result = crit.list();
        assert result.size() == 1;

        // Ordering results
        crit = session.createCriteria(User.class)
                        .addOrder( Order.asc("lastname") )
                        .addOrder( Order.asc("firstname") );
        result = crit.list();
        assert result.size() == 2;
        assert ((User)result.get(0)).getUsername().equals("johndoe");

        // End the unit of work
        HibernateUtil.getSessionFactory().getCurrentSession()
                        .getTransaction().commit();
    }

    @Test(groups = "integration-hibernate")
    public void joinCriteriaQueries() {

        // Start a unit of work
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Criteria crit;
        List result;

        // Non-distinct results are returned
        crit = session.createCriteria(Item.class)
                        .setFetchMode("bids", FetchMode.JOIN)
                        .setResultTransformer(Criteria.ROOT_ENTITY);
        result = crit.list();
        assert result.size() == 3; // Only 2 items in the database - join fetch duplicates

        // Filtering duplicates with a transformer
        crit = session.createCriteria(Item.class)
                        .setFetchMode("bids", FetchMode.JOIN)
                        .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        result = crit.list();
        assert result.size() == 2;

        // Using a transformer that returns a product result in a Map
        crit = session.createCriteria(Item.class)
                        .createAlias("bids", "b")
                        .createAlias("seller", "s")
                        .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        result = crit.list();
        for (Object aResult : result) {
            Map map = (Map) aResult;
            Item item   = (Item) map.get(Criteria.ROOT_ALIAS);
            Bid  bid    = (Bid)  map.get("b");
            User seller = (User) map.get("s");
        }

        // End the unit of work
        HibernateUtil.getSessionFactory().getCurrentSession()
                        .getTransaction().commit();
    }

    @Test(groups = "integration-hibernate")
    public void projectionCriteriaQueries() {

        // Start a unit of work
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Criteria crit;
        List result;

        // Return only item identifiers
        crit = session.createCriteria(Item.class)
                        .setProjection( Projections.id() );
        result = crit.list();
        assert result.size() == 2;
        assert result.get(0) instanceof Long;

        // Return several attributes as an Object[] -- multicolumn projection not supported?
        crit = session.createCriteria(Item.class)
                        .setProjection( Projections.projectionList()
                            .add( Projections.id() )
                            .add( Projections.property("description") )
                            .add( Projections.property("initialPrice.value") )
                            .add( Projections.property("initialPrice.currency") )
                        );
        result = crit.list();
        assert result.size() == 2;
        assert result.get(0) instanceof Object[];

        // Alternative with Property.forName()
        crit = session.createCriteria(Item.class)
                        .setProjection( Projections.projectionList()
                            .add( Property.forName("id") )
                            .add( Property.forName("description") )
                            .add( Property.forName("initialPrice.value") )
                            .add( Property.forName("initialPrice.currency") )
                        );
        result = crit.list();
        assert result.size() == 2;
        assert result.get(0) instanceof Object[];

        // Alternative with dynamic instantiation through transformer
        crit = session.createCriteria(Item.class)
                        .setProjection( Projections.projectionList()
                            .add( Projections.id().as("itemId") )
                            .add( Projections.property("description").as("itemDescription") )
                            .add( Projections.property("initialPrice.value").as("itemInitialPriceValue") )
                            .add( Projections.property("initialPrice.currency").as("itemInitialPriceCurrency") )
                        ).setResultTransformer(new AliasToBeanResultTransformer(ItemPriceData.class) );
        result = crit.list();
        assert result.size() == 2;
        assert result.get(0) instanceof ItemPriceData;

        // Count number of rows
        crit = session.createCriteria(Item.class)
                        .setProjection( Projections.rowCount() );
        result = crit.list();
        assert result.size() == 1;
        assert ((Integer)result.get(0)).equals(2);

        // Aggregation and grouping
        crit = session.createCriteria(Bid.class)
                        .createAlias("bidder", "u")
                        .setProjection( Projections.projectionList()
                            .add( Property.forName("u.id").group() )
                            .add( Property.forName("u.username").group() )
                            .add( Property.forName("id").count())
                            .add( Property.forName("amount.value").avg() )
                        );
        result = crit.list();
        assert result.size() == 1;
        assert result.get(0) instanceof Object[];

        // Aggregation and grouping, alternative syntax
        crit = session.createCriteria(Bid.class)
                        .createAlias("bidder", "u")
                        .setProjection( Projections.projectionList()
                            .add( Projections.groupProperty("u.id") )
                            .add( Projections.groupProperty("u.username").as("uname") )
                            .add( Projections.count("id") )
                            .add( Projections.avg("amount.value") )
                        ).addOrder( Order.asc("uname") );
        result = crit.list();
        assert result.size() == 1;
        assert result.get(0) instanceof Object[];

        // SQL projection
        crit = session.createCriteria(Bid.class)
                        .createAlias("bidder", "u")
                        .setProjection( Projections.projectionList()
                            .add( Projections.groupProperty("u.id") )
                            .add( Projections.groupProperty("u.username") )
                            .add( Projections.count("id") )
                            .add( Projections.avg("amount.value") )
                            .add( Projections.sqlProjection(
                                    "(select count(*) from ITEM i where i.ITEM_ID = ITEM_ID) as numOfItems",
                                    new String[] { "numOfItems" },
                                    new Type[] { Hibernate.LONG }
                                  )
                            )
                        );
        result = crit.list();
        assert result.size() == 1;
        assert result.get(0) instanceof Object[];

        // End the unit of work
        HibernateUtil.getSessionFactory().getCurrentSession()
                        .getTransaction().commit();
    }


    public static class ItemPriceData {
        public Long itemId;
        public String itemDescription;
        public BigDecimal itemInitialPriceValue;
        public Currency itemInitialPriceCurrency;
    }



}
