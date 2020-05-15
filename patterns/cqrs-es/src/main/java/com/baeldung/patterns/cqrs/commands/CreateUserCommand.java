package com.baeldung.patterns.cqrs.commands;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserCommand {

    private String userId;
    private String firatName;
    private String lastName;

}
