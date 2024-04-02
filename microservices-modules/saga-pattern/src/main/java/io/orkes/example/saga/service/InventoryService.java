package io.orkes.example.saga.service;

import io.orkes.example.saga.dao.InventoryDAO;
import io.orkes.example.saga.pojos.FoodItem;
import io.orkes.example.saga.pojos.Restaurant;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
public class InventoryService {

    private static final InventoryDAO inventoryDAO = new InventoryDAO("jdbc:sqlite:food_delivery.db");

    public static boolean checkAvailability(int restaurantId, ArrayList<FoodItem> items) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setName("");
        inventoryDAO.readRestaurant(restaurantId, restaurant);
        return !Objects.equals(restaurant.getName(), "");
    }
}
