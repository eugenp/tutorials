package org.hibernate.caveatemptor.tutorial4.auction.dao.hibernate;

import org.hibernate.Query;
import org.hibernate.caveatemptor.tutorial4.auction.dao.ItemDAO;
import org.hibernate.caveatemptor.tutorial4.auction.model.Bid;
import org.hibernate.caveatemptor.tutorial4.auction.model.Item;

/**
 * Hibernate-specific implementation of the <tt>ItemDAO</tt>
 * non-CRUD data access object.
 *
 * @author Christian Bauer
 */
public class ItemDAOHibernate extends GenericHibernateDAO<Item, Long> implements ItemDAO {

    public Bid getMaxBid(Long itemId) {
        Query q = getSession().getNamedQuery("getItemMaxBid");
        q.setParameter("itemid", itemId);
        return (Bid) q.uniqueResult();
    }

    public Bid getMinBid(Long itemId) {
        Query q = getSession().getNamedQuery("getItemMinBid");
        q.setParameter("itemid", itemId);
        return (Bid) q.uniqueResult();
    }

}
