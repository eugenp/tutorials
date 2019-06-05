package com.hexagonal.repository;

import java.util.List;


public interface InventoryRepository {

    public boolean addItem(String itemId);

    public boolean replaceItem(String itemId);
    
    public boolean deleteItem(String itemId);

    public List<String> findAll();

    public String findItemById(String itemId);

}
