package com.baeldung.boot.shopify;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/shopify")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartInterface ShoppingCartInterface;

    public ShoppingCartController(ShoppingCartInterface ShoppingCartInterface) {
        this.ShoppingCartInterface = ShoppingCartInterface;
    }

    @GetMapping("/items")
    public List<Item> getItemsIntheCart() {
        return ShoppingCartInterface.getItems();
    }

    @PostMapping("/item")
    public void addProduct(@RequestBody Item item) {
        ShoppingCartInterface.addItem(item);
    }

    @DeleteMapping("/{productId}")
    public void removeProduct(@PathVariable int productId) {
        ShoppingCartInterface.removeItem(productId);
    }
}
