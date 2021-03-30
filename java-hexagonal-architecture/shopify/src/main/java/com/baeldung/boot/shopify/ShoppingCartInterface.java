package com.baeldung.boot.shopify;

import java.util.List;

public interface ShoppingCartInterface {

    public List<Item> getItems();

    public void addItem(Item item);

    public void removeItem(int productId);
}
