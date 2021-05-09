package com.baeldung.shopify.adapters.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baeldung.shopify.application.domain.Item;
import com.baeldung.shopify.application.ports.oubound.RetrieveItemRepo;
import com.baeldung.shopify.application.ports.oubound.SaveItemRepo;

@Repository
public class ItemRepository implements SaveItemRepo, RetrieveItemRepo {

	private Map<String, Item> shoppingCard = new HashMap<>();

	@Override
	public void saveItem(Item item) {
		shoppingCard.put(item.getProductId(), item);
	}

	@Override
	public List<Item> retrieveItem() {
		return shoppingCard.values().stream().collect(Collectors.toList());
	}

}
