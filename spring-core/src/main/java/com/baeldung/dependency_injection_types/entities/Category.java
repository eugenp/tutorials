package com.baeldung.dependency_injection_types.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class Category {

    private String code;
    private String name;
}
