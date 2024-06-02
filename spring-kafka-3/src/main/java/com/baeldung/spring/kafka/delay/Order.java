package com.baeldung.spring.kafka.delay;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private UUID orderId;

    private LocalDateTime orderGeneratedDateTime;

    private LocalDateTime orderProcessedTime;

    private List<String> address;

    private double price;
}
