package com.baeldung.libraries.smooks.model;

public class Item {

    public Item() {
    }

    public Item(String code, Double price, Integer quantity) {
        this.code = code;
        this.price = price;
        this.quantity = quantity;
    }

    private String code;
    private Double price;
    private Integer quantity;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Item item = (Item) o;

        if (code != null ? !code.equals(item.code) : item.code != null)
            return false;
        if (price != null ? !price.equals(item.price) : item.price != null)
            return false;
        return quantity != null ? quantity.equals(item.quantity) : item.quantity == null;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" + "code='" + code + '\'' + ", price=" + price + ", quantity=" + quantity + '}';
    }
}
