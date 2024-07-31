package io.orkes.example.saga.pojos;

import lombok.Data;

import java.util.ArrayList;

@Data
public class FoodDeliveryRequest {
    private String customerEmail;
    private String customerName;
    private String customerContact;
    private int restaurantId;
    private ArrayList<Object> foodItems;
    private ArrayList<String> additionalNotes;
    private String address;
    private String deliveryInstructions;
    private double paymentAmount;
    private Object paymentMethod;
}
