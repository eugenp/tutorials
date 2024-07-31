package io.orkes.example.saga.pojos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FoodItem {
    private String item;
    private int quantity;
}
