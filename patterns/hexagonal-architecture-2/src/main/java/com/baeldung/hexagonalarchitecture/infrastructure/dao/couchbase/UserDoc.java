package com.baeldung.hexagonalarchitecture.infrastructure.dao.couchbase;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import com.baeldung.hexagonalarchitecture.domain.model.User;
import com.baeldung.hexagonalarchitecture.domain.model.UserStatus;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class UserDoc {

    @Id
    private String id;

    @Field
    @NotNull
    private String username;

    @Field
    @NotNull
    private UserStatus status;

    @Field
    private LocalDateTime activeSince;

    @Field
    private LocalDateTime activeUntil;

    public UserDoc(User user) {
        id = UUID.randomUUID()
            .toString();
        username = user.getUsername();
        status = user.getStatus();
        activeSince = user.getActiveSince();
        activeUntil = user.getActiveUntil();
    }

    public User toUser() {
        return User.builder()
            .username(username)
            .status(status)
            .activeSince(activeSince)
            .activeUntil(activeUntil)
            .build();
    }
}
