package com.baeldung.architecture.clean.hexagonal.entitybased.user.infrastructure.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO {
    private Long id;
    private LocalDateTime lastModified;
}
