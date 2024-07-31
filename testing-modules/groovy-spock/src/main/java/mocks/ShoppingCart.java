package mocks;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private final Map<String, Integer> items = new HashMap<>();
    private double totalPrice = 0.0;

    public void addItem(String item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) + quantity);
        totalPrice += quantity * 2.00; // Example pricing
    }

    public int getTotalItems() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean containsItem(String item) {
        return items.containsKey(item);
    }
}