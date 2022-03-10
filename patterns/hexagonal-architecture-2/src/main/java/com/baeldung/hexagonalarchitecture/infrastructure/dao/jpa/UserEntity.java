package com.baeldung.hexagonalarchitecture.infrastructure.dao.jpa;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baeldung.hexagonalarchitecture.domain.model.User;
import com.baeldung.hexagonalarchitecture.domain.model.UserStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "USER")
public class UserEntity {

    @Id
    private String id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "STATUS")
    private UserStatus status;

    @Column(name = "ACTIVE_SINCE")
    private LocalDateTime activeSince;

    @Column(name = "ACTIVE_UNTIL")
    private LocalDateTime activeUntil;

    public User toUser() {
        return User.builder()
            .username(username)
            .status(status)
            .activeSince(activeSince)
            .activeUntil(activeUntil)
            .build();
    }

    public UserEntity(User user) {
        id = UUID.randomUUID()
            .toString();
        username = user.getUsername();
        status = user.getStatus();
        activeSince = user.getActiveSince();
        activeUntil = user.getActiveUntil();
    }
}
