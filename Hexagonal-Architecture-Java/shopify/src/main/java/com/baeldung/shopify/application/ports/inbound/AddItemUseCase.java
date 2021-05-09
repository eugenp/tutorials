package com.baeldung.shopify.application.ports.inbound;

import com.baeldung.shopify.application.domain.Item;

public interface AddItemUseCase {

	public void addItem(Item item);

}
