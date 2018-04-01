package org.hibernate.caveatemptor.tutorial3.auction.dao;

import auction.model.User;


/**
 * Business DAO operations related to the <tt>User</tt> entity.
 *
 * @see auction.model.User
 *
 * @author Christian Bauer
 */
public interface UserDAO extends GenericDAO<User, Long> { }
