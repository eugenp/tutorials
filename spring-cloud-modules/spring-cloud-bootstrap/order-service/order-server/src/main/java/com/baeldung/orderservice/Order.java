package com.baeldung.orderservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Integer id;
    private Integer customerId;
    private String itemId;
    private String date;

}
