package service.impl;

import entities.GroceryCart;
import org.springframework.beans.factory.annotation.Autowired;
import repositories.GroceryStoreRepositoryPort;
import service.GroceryStoreServicePort;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * The implementation layer for core business logic
 */
public class GroceryStoreServicePortAdapter implements GroceryStoreServicePort {


    /**
     * Spring resolved bean for repo.
     * It acts as port for communication with database
     */
    @Autowired
    GroceryStoreRepositoryPort groceryStoreRepositoryPort;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addItem(Long itemId) {
        BigDecimal itemPrice = new BigDecimal(8999);

        GroceryCart groceryCart = new GroceryCart();
        groceryCart.setId(1L);
        groceryCart.setPrice(itemPrice);
        groceryCart.setQuantity(10);

        groceryStoreRepositoryPort.save(groceryCart);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteItem(Long itemId) {
        Long l = new Long(itemId);
        int id = l.intValue();
        Optional<GroceryCart> groceryCartInstance =   groceryStoreRepositoryPort.findById(id);
        GroceryCart cart = groceryCartInstance.get();
        groceryStoreRepositoryPort.delete(cart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fetchAllItems() {
        Iterable<GroceryCart> groceryList  = groceryStoreRepositoryPort.findAll();

        //code to Iterate and fetch all items {...}
    }
}
