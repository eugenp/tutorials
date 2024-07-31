package io.orkes.example.saga.pojos;

import lombok.Data;

@Data
public class Customer {
    private int id;
    private String email;
    private String name;
    private String contact;
}
