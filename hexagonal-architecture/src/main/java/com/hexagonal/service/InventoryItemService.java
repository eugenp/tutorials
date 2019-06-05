package com.hexagonal.service;

public interface InventoryItemService {
	
	public int checkQty(String itemId);
	
	public int checkQtyOnHand(String itemId);
	
	public boolean updateQtyOnHand(String itemId, int qty);
	
	public boolean updateQty(String itemId, int qty);
	
	public String searchItemd(String itemId);
	
	public boolean updateItem(String itemId);
}
