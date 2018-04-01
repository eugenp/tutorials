package org.hibernate.caveatemptor.tutorial3.auction.dao;

import auction.model.Item;
import auction.model.Bid;


/**
 * Business DAO operations related to the <tt>Item</tt> entity.
 * <p>
 * Note the usage of the constants for named query declaration. That way,
 * naming of queries is centralized and checked at compile time. Of course,
 * this can be broken by externalizing queries into XML metadata, but it
 * is type-safe for queries declared in annotations. See the entity class
 * source for an example.
 *
 * @see auction.model.Item
 *
 * @author Christian Bauer
 */
public interface ItemDAO extends GenericDAO<Item, Long> {

    Bid getMaxBid(Item item);
    Bid getMinBid(Item item);
    public Item fetchWithBids(Item item);

}
