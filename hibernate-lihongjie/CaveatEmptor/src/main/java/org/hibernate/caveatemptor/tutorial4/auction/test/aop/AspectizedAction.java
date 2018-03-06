package org.hibernate.caveatemptor.tutorial4.auction.test.aop;

import org.dbunit.operation.DatabaseOperation;
import org.testng.annotations.Test;
import auction.dao.DAOFactory;
import auction.web.actions.PlaceBidAction;
import auction.web.actions.Action;
import auction.test.HibernateIntegrationTest;

import java.util.*;
import java.math.BigDecimal;

/**
 * Test an action wrapped with an AOP interceptor for Session handling.
 *
 * @author Christian Bauer
 */
public class AspectizedAction extends HibernateIntegrationTest {

    protected void prepareSettings() {
        dataSetLocation = "auction/test/basedata.xml";
        beforeTestOperations.add(DatabaseOperation.CLEAN_INSERT);
    }

    @Test(groups = "integration-hibernate")
    public void callWrappedAction() {

        // Prepare some input
        Map<String, Object> event = new HashMap<String, Object>();
        event.put(PlaceBidAction.INPUT_ITEM_ID, 1l);
        event.put(PlaceBidAction.INPUT_USER_ID, 1l);
        event.put(PlaceBidAction.INPUT_BID_AMOUNT, new BigDecimal("1234.99"));

        // Execute the service method, interceptor aspect applies transaction
        DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);
        Action placeBidAction = new PlaceBidAction(daoFactory);
        placeBidAction.execute(event);

        // Check if all good
        assert event.get(PlaceBidAction.OUTPUT_NEW_BID) != null;
    }

}
