package com.baeldung.shopify.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.shopify.adapters.persistence.ItemRepository;
import com.baeldung.shopify.application.domain.Item;
import com.baeldung.shopify.application.ports.inbound.AddItemUseCase;
import com.baeldung.shopify.application.ports.inbound.GetItemUseCase;

@Service
public class ShoppingCartService implements AddItemUseCase, GetItemUseCase {

	@Autowired
	private ItemRepository itemRepository;

	@Override
	public void addItem(Item item) {
		itemRepository.saveItem(item);
	}

	@Override
	public List<Item> getItems() {
		return itemRepository.retrieveItem();
	}

}
