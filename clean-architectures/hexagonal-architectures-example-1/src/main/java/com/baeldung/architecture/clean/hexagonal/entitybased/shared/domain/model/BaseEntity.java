package com.baeldung.architecture.clean.hexagonal.entitybased.shared.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {
    private Long id;
    private LocalDateTime lastModified;
}
