package com.baeldung.pattern.hexagonal.dao;

import com.baeldung.pattern.hexagonal.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String login;
    private String name;
    private String surname;

    public static User fromEntity(UserEntity entity) {
        return User.builder()
                .login(entity.getLogin())
                .name(entity.getName())
                .surname(entity.getSurname())
                .build();
    }
}
