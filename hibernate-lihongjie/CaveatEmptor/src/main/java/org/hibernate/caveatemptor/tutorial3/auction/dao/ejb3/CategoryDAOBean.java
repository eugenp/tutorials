package org.hibernate.caveatemptor.tutorial3.auction.dao.ejb3;

import auction.dao.CategoryDAO;
import auction.model.Category;

import javax.ejb.*;
import java.util.List;

/**
 * EJB 3.0 implementation of the <tt>CategoryDAO</tt> non-CRUD data access object.
 *
 * @author Christian Bauer
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CategoryDAOBean
        extends     GenericEJB3DAO<Category, Long>
        implements  CategoryDAO {

    @SuppressWarnings("unchecked")
    public List<Category> findAll(boolean onlyRootCategories) {
        if (onlyRootCategories)
            return getEntityManager().createQuery("select c from "+ getEntityBeanType().getName() + " c" +
                                                  " where c.parentCategory is null").getResultList();
            // Or bind ourself to a Hibernate extension and use findByCriteria(Criterion...) from superclass?
        else
            return findAll();
    } 

}
