package com.baeldung.jersey.server;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PathParam;

public class ItemParam {

    @HeaderParam("headerParam")
    private String shopKey;

    @PathParam("pathParam")
    private String itemId;

    @FormParam("formParam")
    private String price;

    public String getShopKey() {
        return shopKey;
    }

    public void setShopKey(String shopKey) {
        this.shopKey = shopKey;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ItemParam{shopKey='" + shopKey + ", itemId='" + itemId + ", price='" + price + '}';
    }
}
