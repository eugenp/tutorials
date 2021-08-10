package com.baeldung.pattern.hexagonal.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@EqualsAndHashCode(of = "email")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String email;
    private String phone;
}