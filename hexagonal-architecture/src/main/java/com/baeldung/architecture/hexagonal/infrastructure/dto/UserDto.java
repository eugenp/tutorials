package com.baeldung.architecture.hexagonal.infrastructure.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String name;
    private String password;

}
