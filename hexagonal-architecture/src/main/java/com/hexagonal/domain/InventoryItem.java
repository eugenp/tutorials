package com.hexagonal.domain;

import java.math.BigDecimal;


public class InventoryItem {

    private int version;
    private String itemId;
    private String itemName;
    private BigDecimal price;
    private int qtyOnHand;
    private int qtyOnReserve;
    private int qty;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemIde(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

	public int getQtyOnHand() {
		return qtyOnHand;
	}

	public void setQtyOnHand(int qtyOnHand) {
		this.qtyOnHand = qtyOnHand;
	}

	public int getQtyOnReserve() {
		return qtyOnReserve;
	}

	public void setQtyOnReserve(int qtyOnReserve) {
		this.qtyOnReserve = qtyOnReserve;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

}
