package org.baeldung.beaninjection;

import org.springframework.stereotype.Component;

@Component
public class StockDaoImpl implements StockDao {

    @Override
    public void update(Product product) {
        System.out.println("Updating quantitiy of product " + product.getName()
          + " to " + product.getQuantity() + " items.");
    }

}
