package org.hibernate.caveatemptor.tutorial4.auction.test.basic;

import auction.persistence.HibernateUtil;
import auction.test.HibernateIntegrationTest;
import org.hibernate.*;
import org.testng.annotations.*;
import org.dom4j.*;
import org.dbunit.operation.DatabaseOperation;

import java.util.*;
import java.math.BigDecimal;
import java.io.*;

/**
 * Dynamic models (maps of maps) and XML representation
 *
 * @author Christian Bauer
 */
public class MapAndXMLRepresentation extends HibernateIntegrationTest {

    protected void prepareSettings() {
        dataSetLocation = "auction/test/basedata.xml";
        beforeTestOperations.add(DatabaseOperation.CLEAN_INSERT);
    }

    @BeforeMethod(groups = "integration-hibernate")
    public void startUoW() {
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
    }

    @AfterMethod(groups = "integration-hibernate")
    public void endHibernateUnit() {
        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
    }

    private Map storeData() {
        Map user = new HashMap();
        user.put("username", "johndoe");

        Map item1 = new HashMap();
        item1.put("description", "An item for auction");
        item1.put("initialPrice", new BigDecimal(99));
        item1.put("seller", user);

        Map item2 = new HashMap();
        item2.put("description", "Another item for auction");
        item2.put("initialPrice", new BigDecimal(123));
        item2.put("seller", user);

        Collection itemsForSale = new ArrayList();
        itemsForSale.add(item1);
        itemsForSale.add(item2);
        user.put("itemsForSale", itemsForSale);

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.save("UserEntity", user);

        session.flush();
        session.clear();

        Map storedData = new HashMap();
        storedData.put("item1", item1);
        storedData.put("item2", item2);
        storedData.put("user", user);
        return storedData;
    }


    @Test(groups = "integration-hibernate")
    public void workWithMaps() {
        Map storedData = storeData();

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Map loadedItem =
                (Map) session.load("ItemEntity",
                                   (Serializable)((Map)storedData.get("item1")).get("id") );

        assert (loadedItem.get("initialPrice")).equals(new BigDecimal(99));

        loadedItem.put("initialPrice", new BigDecimal(100));

        session.flush();
        session.clear();

        loadedItem =
                (Map) session.load("ItemEntity",
                        (Serializable)((Map)storedData.get("item1")).get("id") );

        assert (loadedItem.get("initialPrice")).equals(new BigDecimal(100));

        session.flush();
        session.clear();

        List queriedItems =
                session.createQuery("from ItemEntity where initialPrice >= :minPrice")
                        .setParameter("minPrice", new BigDecimal(100))
                        .list();

        assert queriedItems.size() == 2;

    }

    @Test(groups = "integration-hibernate")
    public void workWithXML() {
        Map storedData = storeData();

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Session dom4jSession = session.getSession(EntityMode.DOM4J);

        Element xmlItem = (Element) dom4jSession
                .get("ItemEntity",
                      (Serializable)((Map)storedData.get("item1")).get("id") );

        Element xmlUser = (Element) dom4jSession
                .get("UserEntity",
                      (Serializable)((Map)storedData.get("user")).get("id") );

        assert xmlItem.attributes().size() == 2;
        assert xmlUser.attributes().size() == 2;

        /* Pretty Print:
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter( System.out, format);
            writer.write( xmlItem);
            writer.write( xmlUser);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        <item id="2" seller-id="1">
          <item-details initial-price="99" description="An item for auction"/>
        </item>

        <user id="1" username="david">
          <items-for-sale>
            <item id="2" seller-id="1">
              <item-details initial-price="99" description="An item for auction"/>
            </item>
            <item id="3" seller-id="1">
              <item-details initial-price="123" description="Another item for auction"/>
            </item>
          </items-for-sale>
        </user>
        */

        session.flush();
        session.clear();

        xmlItem = (Element) dom4jSession
                .get("ItemEntity",
                      (Serializable)((Map)storedData.get("item1")).get("id") );

        xmlItem.element("item-details")
                .attribute("initial-price")
                 .setValue("100");

        session.flush();
        session.clear();

        xmlItem = (Element) dom4jSession
                .get("ItemEntity",
                      (Serializable)((Map)storedData.get("item1")).get("id") );

        assert xmlItem.element("item-details")
                .attributeValue("initial-price").equals("100");

        session.flush();
        session.clear();

        xmlUser = (Element) dom4jSession
                .get("UserEntity",
                      (Serializable)((Map)storedData.get("user")).get("id") );

        Element newItem = DocumentHelper.createElement("item");
        Element newItemDetails = newItem.addElement("item-details");
        newItem.addAttribute("seller-id", xmlUser.attribute("id").getValue() );
        newItemDetails.addAttribute("initial-price", "222");
        newItemDetails.addAttribute("description", "A third item");

        dom4jSession.save("ItemEntity", newItem);

        session.flush();
        session.clear();

        Element loadedItem = (Element) dom4jSession
                .get("ItemEntity", new Long(newItem.attribute("id").getValue()));

        assert loadedItem.attribute("seller-id").getValue()
                .equals( xmlUser.attribute("id").getValue() );

    }

}
