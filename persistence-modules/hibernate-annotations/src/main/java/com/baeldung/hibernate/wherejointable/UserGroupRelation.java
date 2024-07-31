package com.baeldung.hibernate.wherejointable;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity(name = "r_user_group")
public class UserGroupRelation implements Serializable {

    @Id
    @Column(name = "user_id", insertable = false, updatable = false)
    private final Long userId;

    @Id
    @Column(name = "group_id", insertable = false, updatable = false)
    private final Long groupId;

    @Enumerated(EnumType.STRING)
    private final UserGroupRole role;

    public UserGroupRelation(Long userId, Long groupId, UserGroupRole role) {
        this.userId = userId;
        this.groupId = groupId;
        this.role = role;
    }

}
