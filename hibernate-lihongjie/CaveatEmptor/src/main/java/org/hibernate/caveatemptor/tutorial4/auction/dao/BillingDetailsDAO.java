package org.hibernate.caveatemptor.tutorial4.auction.dao;

import auction.model.*;
import org.hibernate.caveatemptor.tutorial3.auction.model.BillingDetails;

import java.util.List;

/**
 * Business DAO operations related to the <tt>BillingDetails</tt> entity.
 * <p>
 * @see auction.model.BillingDetails
 * @see auction.model.CreditCard
 * @see auction.model.BankAccount
 *
 * @author Christian Bauer
 */
public interface BillingDetailsDAO extends GenericDAO<BillingDetails, Long> {

    public List<BillingDetails> findConcrete(Class concreteClass);

}
