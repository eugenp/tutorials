package org.hibernate.caveatemptor.tutorial4.auction.command;

import auction.model.*;
import auction.dao.*;
import auction.exceptions.*;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * An example of the EJB command pattern.
 * <p>
 * Some parameters are passed in, the control logic is executed, the
 * result comes back.
 *
 * @author Christian Bauer
 */
public class BidForItemCommand extends DataAccessCommand {

    private Long userId;
	private Long itemId;
	private BigDecimal bidAmount;

	private Bid newBid;

	public BidForItemCommand(Long userId,
							 Long itemId,
							 BigDecimal bidAmount) {
		this.userId = userId;
		this.itemId = itemId;
		this.bidAmount = bidAmount;
	}

	public Bid getNewBid() {
		return newBid;
	}

	public void execute() throws CommandException {

        ItemDAO itemDAO = daoFactory.getItemDAO();
        UserDAO userDAO = daoFactory.getUserDAO();

        try {

			MonetaryAmount newAmount =
			        new MonetaryAmount(bidAmount, Currency.getInstance("USD"));
			Bid currentMaxBid = itemDAO.getMaxBid(itemId);
			Bid currentMinBid = itemDAO.getMinBid(itemId);

			Item item = itemDAO.findById(itemId, true);
			newBid = item.placeBid(userDAO.findById(userId, false),
									newAmount,
									currentMaxBid,
			                        currentMinBid);

		} catch (BusinessException ex) {
			// Rethrow as a checked exception that can trigger rollback
			throw new CommandException(ex);
        }
	}

}
