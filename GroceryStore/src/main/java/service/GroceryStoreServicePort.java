package service;

/**
 * This interface acts as the port of our application
 */
public interface GroceryStoreServicePort {

    //Port that will add an item  with specified id
    void addItem(Long itemId);

    //Port that will delete an item  with specified id
    void deleteItem(Long itemId);

    //Port that will fetch all items added so far
    void fetchAllItems();
}
