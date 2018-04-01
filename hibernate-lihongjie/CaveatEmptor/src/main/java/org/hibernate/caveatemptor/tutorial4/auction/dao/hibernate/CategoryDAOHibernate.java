package org.hibernate.caveatemptor.tutorial4.auction.dao.hibernate;

import auction.dao.CategoryDAO;
import auction.model.Category;
import org.hibernate.criterion.Expression;

import java.util.Collection;

/**
 * Hibernate-specific implementation of the <tt>CategoryDAO</tt>
 * non-CRUD data access object.
 *
 * @author Christian Bauer
 */
public class CategoryDAOHibernate
        extends     GenericHibernateDAO<Category, Long>
        implements  CategoryDAO {

    public Collection<Category> findAll(boolean onlyRootCategories) {
        if (onlyRootCategories)
            return findByCriteria( Expression.isNull("parent") );
        else
            return findAll();
    }
}
