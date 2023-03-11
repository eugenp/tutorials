package com.baeldung.spring.kafka.multiplelisteners;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookEvent {

    private String title;
    private String description;
    private Double price;
}
