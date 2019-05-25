package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.GroceryStoreServicePort;

/**
 * Controller layer acts as the adapter to communicate with ports
 */
@RestController
public class GroceryStoreControllerAdapter {
    //Spring resolved service bean. The service bean caters to the application's core business logic
    @Autowired
    GroceryStoreServicePort groceryStoreService;

    @PostMapping(value = "/add/{itemId}")
    public void addItem(@RequestParam Long itemId) {
        groceryStoreService.addItem(itemId);
    }

    @DeleteMapping("/delete/{itemId}")
    public void deleteItem(@RequestParam Long itemId) {
        groceryStoreService.deleteItem(itemId);
    }

    @GetMapping("/fetch/all/items")
    public void fetchAllItems() {
        groceryStoreService.fetchAllItems();
    }
}
