package org.hibernate.caveatemptor.tutorial3.auction.dao.ejb3;

import auction.model.Bid;
import auction.model.Item;
import auction.dao.ItemDAO;

import javax.ejb.*;

import java.util.List;

/**
 * EJB3-specific implementation of the <tt>ItemDAO</tt> non-CRUD data access object.
 *
 * @author Christian Bauer
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ItemDAOBean extends GenericEJB3DAO<Item, Long> implements ItemDAO {

    public Bid getMaxBid(Item item) {
        // TODO: em.getSingleResult() doesn't return null but throws an exception, how inconvenient
        Bid currentMaxBid = null;
        List result = getEntityManager()
                            .createNamedQuery("Item-getMaxBid")
                             .setParameter("itemId", item.getId())
                             .getResultList();
        if (result.size() > 0) currentMaxBid = (Bid)result.get(0);
        return currentMaxBid;
    }

    public Bid getMinBid(Item item) {
        // TODO: em.getSingleResult() doesn't return null but throws an exception, how inconvenient
        Bid currentMinBid = null;
        List result = getEntityManager()
                             .createNamedQuery("Item-getMinBid")
                             .setParameter("itemId", item.getId())
                             .getResultList();
        if (result.size() > 0) currentMinBid = (Bid)result.get(0);
        return currentMinBid;
    }

    public Item fetchWithBids(Item item) {
        return (Item) getEntityManager()
                .createQuery("select i from Item i left join fetch i.bids where i = :item")
                 .setParameter("item", item)
                 .getSingleResult();
    }

}
