package com.baeldung.shopify.application.ports.inbound;

import java.util.List;

import com.baeldung.shopify.application.domain.Item;

public interface GetItemUseCase {

	public List<Item> getItems();

}
