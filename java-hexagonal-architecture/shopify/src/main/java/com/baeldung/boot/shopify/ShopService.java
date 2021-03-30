package com.baeldung.boot.shopify;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopService implements ShoppingCartInterface {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        itemRepository.findAll()
            .forEach(item -> items.add(item));
        return items;
    }

    @Override
    public void addItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public void removeItem(int productId) {
        itemRepository.deleteById(productId);
    }
}
