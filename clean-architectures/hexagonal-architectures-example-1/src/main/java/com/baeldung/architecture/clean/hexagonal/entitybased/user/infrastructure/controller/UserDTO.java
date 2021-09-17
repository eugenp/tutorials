package com.baeldung.architecture.clean.hexagonal.entitybased.user.infrastructure.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends BaseDTO {
    private String name;
    private String surname;
    private String documentID;
}
