package io.orkes.example.saga.pojos;

import lombok.Data;

import java.util.ArrayList;

@Data
public class OrderRequest {
    private String OrderRequestId;
    private String customerEmail;
    private String customerName;
    private String customerContact;
    private int restaurantId;
    private ArrayList<FoodItem> items;
    private ArrayList<String> notes;
    private String deliveryAddress;
    private String deliveryInstructions;
}
