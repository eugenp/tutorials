package controller;

import domain.Item;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import ports.ItemService;

@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<Item>> getProducts() {
        return new ResponseEntity<List<Item>>(itemService.getItems(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Item> getProductById(@PathVariable Integer itemId) {
        return new ResponseEntity<Item>(itemService.getItem(itemId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Item> addProduct(@RequestBody Item item) {
        return new ResponseEntity<Item>(itemService.addNewItem(item), HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Item> removeProduct(@PathVariable int id) {
        return new ResponseEntity<Item>(itemService.removeItem(id), HttpStatus.OK);
    }
}
