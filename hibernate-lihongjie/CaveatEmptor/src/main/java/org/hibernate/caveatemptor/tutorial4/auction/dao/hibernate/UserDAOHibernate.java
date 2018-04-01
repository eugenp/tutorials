package org.hibernate.caveatemptor.tutorial4.auction.dao.hibernate;

import auction.dao.UserDAO;
import auction.model.*;
import org.hibernate.Criteria;
import org.hibernate.caveatemptor.tutorial3.auction.model.AddressEntity;
import org.hibernate.caveatemptor.tutorial3.auction.model.User;

import static org.hibernate.criterion.Expression.eq;

/**
 * Hibernate-specific implementation of the <tt>UserDAO</tt>
 * non-CRUD data access object.
 *
 * @author Christian Bauer
 */
public class UserDAOHibernate extends GenericHibernateDAO<User, Long> implements UserDAO {

    public User validateLogin(User user) {
        Criteria crit = getSession().createCriteria(getPersistentClass());
        crit.add( eq("username", user.getUsername()) );
        crit.add( eq("password", user.getPassword()) );
        return (User)crit.uniqueResult();
    }

    public void persistAddress(AddressEntity address) {
        getSession().save(address);
    }

}

