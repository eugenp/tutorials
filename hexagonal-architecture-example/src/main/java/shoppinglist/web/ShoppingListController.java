package shoppinglist.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shoppinglist.model.ShoppingList;
import shoppinglist.service.ShoppingListService;

@RestController
@RequestMapping("/shoppinglist")
public class ShoppingListController {

	@Autowired
	private ShoppingListService shoppingListService;

	@PostMapping("/{name}")
	public void create(@PathVariable String name) {
		shoppingListService.create(name);

	}

	@PutMapping("/{name}/{product}")
	public void add(@PathVariable String name, @PathVariable String product) {
		shoppingListService.addProduct(name, product);

	}

	@GetMapping("/{name}")
	public ShoppingList get(@PathVariable String name) {
		return shoppingListService.get(name);
	}
}