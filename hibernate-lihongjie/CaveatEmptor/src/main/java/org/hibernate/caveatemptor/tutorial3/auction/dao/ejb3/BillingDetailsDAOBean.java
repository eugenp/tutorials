package org.hibernate.caveatemptor.tutorial3.auction.dao.ejb3;

import auction.dao.BillingDetailsDAO;
import auction.model.BillingDetails;

import javax.ejb.*;
import java.util.*;

/**
 * EJB3-specific implementation of the <tt>CategoryDAO</tt> non-CRUD data access object.
 *
 * @author Christian Bauer
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BillingDetailsDAOBean
        extends     GenericEJB3DAO<BillingDetails, Long>
        implements  BillingDetailsDAO {

    @SuppressWarnings("unchecked")
    public List<BillingDetails> findConcrete(Class concreteClass) {
        return getEntityManager()
                .createQuery("select c from " + concreteClass.getName() + " c").getResultList();
    }
}
