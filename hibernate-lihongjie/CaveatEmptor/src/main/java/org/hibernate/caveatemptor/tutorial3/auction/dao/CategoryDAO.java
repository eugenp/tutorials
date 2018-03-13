package org.hibernate.caveatemptor.tutorial3.auction.dao;


import auction.model.Category;

import java.util.List;

/**
 * Business DAO operations related to the <tt>Category</tt> entity.
 *
 * @see auction.model.Category
 *
 * @author Christian Bauer
 */
public interface CategoryDAO extends GenericDAO<Category, Long> {

    public List<Category> findAll(boolean onlyRootCategories);

}
