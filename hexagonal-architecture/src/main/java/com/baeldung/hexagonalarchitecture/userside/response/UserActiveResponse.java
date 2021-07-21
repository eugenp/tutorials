package com.baeldung.hexagonalarchitecture.userside.response;

import com.baeldung.hexagonalarchitecture.businesslogic.dto.User;

import java.util.UUID;

public class UserActiveResponse {
    private UUID uuid;
    private boolean active;

    public UserActiveResponse(User user) {
        this.uuid = user.getId();
        this.active = user.isActive();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "UserUpdateResponse{" +
                "uuid=" + uuid +
                ", active=" + active +
                '}';
    }
}
