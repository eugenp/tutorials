package com.baeldung.messaging.postgresql.domain;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Table(name = "orders")
public class Order {
    @Id
    private Long id;
    private String symbol;
    private OrderType orderType;
    private BigDecimal price;
    private BigDecimal quantity;
}
