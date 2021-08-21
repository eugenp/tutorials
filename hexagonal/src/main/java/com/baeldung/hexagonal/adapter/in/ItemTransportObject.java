package com.baeldung.hexagonal.adapter.in;

import lombok.Data;

@Data
class ItemTransportObject {
    private String name;
    private Integer quantity;
    private String category;
}
