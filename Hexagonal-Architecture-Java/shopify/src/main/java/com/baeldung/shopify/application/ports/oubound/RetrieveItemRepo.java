package com.baeldung.shopify.application.ports.oubound;

import java.util.List;

import com.baeldung.shopify.application.domain.Item;

public interface RetrieveItemRepo {

	public List<Item> retrieveItem();

}
