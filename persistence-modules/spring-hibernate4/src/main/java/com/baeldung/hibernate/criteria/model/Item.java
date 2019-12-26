package com.baeldung.hibernate.criteria.model;

import java.io.Serializable;

public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer itemId;
    private String itemName;
    private String itemDescription;
    private Integer itemPrice;

    // constructors
    public Item() {

    }

    public Item(final Integer itemId, final String itemName, final String itemDescription) {
        super();
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Item other = (Item) obj;
        if (itemId == null) {
            if (other.itemId != null)
                return false;
        } else if (!itemId.equals(other.itemId))
            return false;
        return true;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(final Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(final String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public Integer getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(final Integer itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemDescription(final String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
