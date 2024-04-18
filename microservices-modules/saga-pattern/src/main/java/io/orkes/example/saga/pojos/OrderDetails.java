package io.orkes.example.saga.pojos;

import lombok.Data;
import java.util.ArrayList;

@Data
public class OrderDetails {
    private String orderId;
    private ArrayList<FoodItem> items;
    private ArrayList<String> notes;
}
