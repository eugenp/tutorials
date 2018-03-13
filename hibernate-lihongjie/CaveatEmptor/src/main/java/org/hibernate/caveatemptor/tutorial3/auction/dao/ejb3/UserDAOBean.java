package org.hibernate.caveatemptor.tutorial3.auction.dao.ejb3;


import auction.model.User;
import auction.dao.UserDAO;

import javax.ejb.*;

/**
 * EJB3-specific implementation of the <tt>UserDataAccess</tt> non-CRUD data access object.
 *
 * @author Christian Bauer
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserDAOBean extends GenericEJB3DAO<User, Long> implements UserDAO { }
