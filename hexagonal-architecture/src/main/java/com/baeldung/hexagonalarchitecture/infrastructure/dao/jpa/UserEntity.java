package com.baeldung.hexagonalarchitecture.infrastructure.dao;

import com.baeldung.hexagonalarchitecture.domain.User;
import com.baeldung.hexagonalarchitecture.domain.UserStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "USER")
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

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
        username = user.getUsername();
        status = user.getStatus();
        activeSince = user.getActiveSince();
        activeUntil = user.getActiveUntil();
    }
}
