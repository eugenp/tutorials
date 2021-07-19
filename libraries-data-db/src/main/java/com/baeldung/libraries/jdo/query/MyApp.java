package com.baeldung.libraries.jdo.query;

import org.datanucleus.api.jdo.JDOPersistenceManagerFactory;
import org.datanucleus.metadata.PersistenceUnitMetaData;

import javax.jdo.JDOQLTypedQuery;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import java.util.List;

public class MyApp {

    private static PersistenceManagerFactory pmf;
    private static PersistenceManager pm;

    public static void main(String[] args) {

        defineDynamicPersistentUnit();
        createTestData();
        queryUsingJDOQL();
        queryUsingTypedJDOQL();
        queryUsingSQL();
        queryUsingJPQL();

    }

    public static void createTestData() {
        ProductItem item1 = new ProductItem("supportedItem", "price less than 10", "SoldOut", 5);
        ProductItem item2 = new ProductItem("pro2", "price less than 10", "InStock", 8);
        ProductItem item3 = new ProductItem("pro3", "price more than 10", "SoldOut", 15);

        if (pm != null) {
            pm.makePersistent(item1);
            pm.makePersistent(item2);
            pm.makePersistent(item3);
        }
    }

    public static void defineDynamicPersistentUnit() {

        PersistenceUnitMetaData pumd = new PersistenceUnitMetaData("dynamic-unit", "RESOURCE_LOCAL", null);
        pumd.addProperty("javax.jdo.option.ConnectionURL", "jdbc:mysql://localhost:3306/jdo_db");
        pumd.addProperty("javax.jdo.option.ConnectionUserName", "root");
        pumd.addProperty("javax.jdo.option.ConnectionPassword", "admin");
        pumd.addProperty("javax.jdo.option.ConnectionDriverName", "com.mysql.jdbc.Driver");
        pumd.addProperty("datanucleus.schema.autoCreateAll", "true");

        pmf = new JDOPersistenceManagerFactory(pumd, null);
        pm = pmf.getPersistenceManager();
    }

    public static void queryUsingJDOQL() {

        Query query = pm.newQuery("SELECT FROM com.baeldung.libraries.jdo.query.ProductItem " + "WHERE price < threshold PARAMETERS double threshold");
        List<ProductItem> explicitParamResults = (List<ProductItem>) query.execute(10);

        query = pm.newQuery("SELECT FROM " + "com.baeldung.libraries.jdo.query.ProductItem WHERE price < :threshold");
        query.setParameters("double threshold");
        List<ProductItem> explicitParamResults2 = (List<ProductItem>) query.execute(10);

        query = pm.newQuery("SELECT FROM " + "com.baeldung.libraries.jdo.query.ProductItem WHERE price < :threshold");
        List<ProductItem> implicitParamResults = (List<ProductItem>) query.execute(10);

    }

    public static void queryUsingTypedJDOQL() {
        JDOQLTypedQuery<ProductItem> tq = pm.newJDOQLTypedQuery(ProductItem.class);
        QProductItem cand = QProductItem.candidate();
        tq = tq.filter(cand.price.lt(10).and(cand.name.startsWith("pro")));
        List<ProductItem> results = tq.executeList();

    }

    public static void queryUsingSQL() {

        Query query = pm.newQuery("javax.jdo.query.SQL", "select * from " + "product_item where price < ? and status = ?");
        query.setClass(ProductItem.class);
        query.setParameters(10, "InStock");
        List<ProductItem> results = query.executeList();

    }

    public static void queryUsingJPQL() {
        Query query = pm.newQuery("JPQL", "select i from " + "com.baeldung.libraries.jdo.query.ProductItem i where i.price < 10" + " and i.status = 'InStock'");
        List<ProductItem> results = (List<ProductItem>) query.execute();

    }

    public static void namedQuery() {
        Query<ProductItem> query = pm.newNamedQuery(ProductItem.class, "PriceBelow10");
        List<ProductItem> results = query.executeList();

    }
}
