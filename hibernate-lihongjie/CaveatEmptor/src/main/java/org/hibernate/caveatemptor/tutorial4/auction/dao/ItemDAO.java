package org.hibernate.caveatemptor.tutorial4.auction.dao;

import auction.model.*;

/**
 * Business DAO operations related to the <tt>Item</tt> entity.
 *
 * @see Item
 * @author Christian Bauer
 */
public interface ItemDAO extends GenericDAO<Item, Long> {

    Bid getMaxBid(Long itemId);
    Bid getMinBid(Long itemId);

}
