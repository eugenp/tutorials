package com.baeldung.pattern.hexagonal.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String login;
    private String name;
    private String surname;
}
