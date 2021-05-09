package com.baeldung.shopify.application.ports.oubound;

import com.baeldung.shopify.application.domain.Item;

public interface SaveItemRepo {

	public void saveItem(Item item);

}
