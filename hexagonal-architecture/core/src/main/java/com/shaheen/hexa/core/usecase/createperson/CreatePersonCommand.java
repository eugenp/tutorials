package com.shaheen.hexa.core.usecase.createperson;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class CreatePersonCommand {
    String name;
    String firstName;
    Integer age;
}