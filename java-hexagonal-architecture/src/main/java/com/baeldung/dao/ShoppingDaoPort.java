package com.baeldung.dao;

/**
 * Shopping DAO interface to expose available database operations to services. 
 */
public interface ShoppingDaoPort {

    public long saveOrder(String productName);

    public void updateInventory();

}
