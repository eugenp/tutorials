package org.hibernate.caveatemptor.tutorial4.auction.web.actions;

import auction.model.*;
import auction.dao.*;
import auction.exceptions.BusinessException;
import org.apache.commons.logging.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * Typical action event handler in any web framework.
 * <p>
 * The primary purpose of this class is to show you how you can
 * use the <tt>SessionTransactionInterceptor</tt> on your action
 * classes, to get transparent
 * database transaction handling. This cross-cutting concern is
 * completely separated from this controller, and the
 * business code in the domain model.
 *
 * @see auction.persistence.SessionTransactionInterceptor
 * @see auction.dao.hibernate.HibernateDAOFactory
 *
 * @author Christian Bauer
 */
public class PlaceBidAction implements Action {

    private static Log log = LogFactory.getLog(PlaceBidAction.class);

    // Just some constants for quick type-safe testing...
    public static final String INPUT_USER_ID     = "userId";
    public static final String INPUT_ITEM_ID     = "itemId";
    public static final String INPUT_BID_AMOUNT  = "bidAmount";
    public static final String OUTPUT_NEW_BID    = "newBid";
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    // Dependencies
    DAOFactory daoFactory;

    public PlaceBidAction(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public String execute(Map event) {
        log.debug("Executing the PlaceBidAction event");

        // We expect that all of this has been validated...
        Long userId = (Long)event.get(INPUT_USER_ID);
        Long itemId = (Long)event.get(INPUT_ITEM_ID);
        BigDecimal bidAmount = (BigDecimal)event.get(INPUT_BID_AMOUNT);

        try {

            ItemDAO itemDAO = daoFactory.getItemDAO();
            UserDAO userDAO = daoFactory.getUserDAO();

            MonetaryAmount newAmount =
                    new MonetaryAmount(bidAmount, Currency.getInstance(Locale.US));
            Bid currentMaxBid = itemDAO.getMaxBid(itemId);
            Bid currentMinBid = itemDAO.getMinBid(itemId);

            Item item = itemDAO.findById(itemId, true);

            Bid newBid = item.placeBid(userDAO.findById(userId, false),
                                    newAmount,
                                    currentMaxBid,
                                    currentMinBid);

            event.put(OUTPUT_NEW_BID, newBid);

            log.debug("Returning successful");
            return "success";

        } catch (BusinessException ex) {
            log.debug("Returning with a failure");
            event.put(BusinessException.ERROR_MSG, ex.getLocalizedMessage());
            return "failure";
        }
    }
}
