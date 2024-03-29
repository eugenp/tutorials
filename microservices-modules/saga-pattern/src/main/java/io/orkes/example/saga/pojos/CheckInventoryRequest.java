package io.orkes.example.saga.pojos;


import lombok.Data;
import java.util.ArrayList;

@Data
public class CheckInventoryRequest {
    private int restaurantId;
    private ArrayList<FoodItem> items;
}
