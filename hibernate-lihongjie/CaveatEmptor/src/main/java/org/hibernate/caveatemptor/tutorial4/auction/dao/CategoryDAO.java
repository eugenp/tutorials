package org.hibernate.caveatemptor.tutorial4.auction.dao;

import auction.model.Category;

import java.util.Collection;

/**
 * Business DAO operations related to the <tt>Category</tt> entity.
 *
 * @see Category
 * @author Christian Bauer
 */
public interface CategoryDAO extends GenericDAO<Category, Long> {

    public Collection<Category> findAll(boolean onlyRootCategories);

}
