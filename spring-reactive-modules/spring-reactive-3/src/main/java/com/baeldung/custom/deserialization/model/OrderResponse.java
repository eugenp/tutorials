package com.baeldung.custom.deserialization.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class OrderResponse {

    private UUID orderId;

    private LocalDateTime orderDateTime;

    private List<String> address;

    private List<String> orderNotes;
}
