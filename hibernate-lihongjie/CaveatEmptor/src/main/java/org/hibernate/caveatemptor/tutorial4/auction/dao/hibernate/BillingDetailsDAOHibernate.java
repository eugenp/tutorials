package org.hibernate.caveatemptor.tutorial4.auction.dao.hibernate;

import auction.dao.BillingDetailsDAO;
import auction.model.*;
import org.hibernate.caveatemptor.tutorial3.auction.model.BillingDetails;

import java.util.List;

/**
 * Hibernate-specific implementation of the <tt>BillingDetailsDAO</tt>
 * non-CRUD data access object.
 *
 * @author Christian Bauer
 */
public class BillingDetailsDAOHibernate extends GenericHibernateDAO<BillingDetails, Long> implements BillingDetailsDAO {

    @SuppressWarnings("unchecked")
    public List<BillingDetails> findConcrete(Class concreteClass) {
        return getSession().createCriteria(concreteClass).list();
    }
}
