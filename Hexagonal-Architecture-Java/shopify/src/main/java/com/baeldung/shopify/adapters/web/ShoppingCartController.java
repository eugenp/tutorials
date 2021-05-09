package com.baeldung.shopify.adapters.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.shopify.application.domain.Item;
import com.baeldung.shopify.application.ports.inbound.AddItemUseCase;
import com.baeldung.shopify.application.ports.inbound.GetItemUseCase;

@RestController
@RequestMapping("/shopify")
public class ShoppingCartController {

	@Autowired
	private AddItemUseCase addItem;

	@Autowired
	private GetItemUseCase getItems;

	@PostMapping
	public void addNewItem(@RequestBody Item item) {
		addItem.addItem(item);
	}

	@GetMapping
	public List<Item> getAllItemsIntheCart() {
		return getItems.getItems();
	}

}
