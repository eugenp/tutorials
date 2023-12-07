package com.baeldung.enums.mapping.user;

public enum UserStatusWithFieldVariable {
    PENDING(ExternalUserStatus.INACTIVE),
    ACTIVE(ExternalUserStatus.ACTIVE),
    BLOCKED(ExternalUserStatus.INACTIVE),
    INACTIVATED_BY_SYSTEM(ExternalUserStatus.INACTIVE),
    DELETED(ExternalUserStatus.INACTIVE);

    private final ExternalUserStatus externalUserStatus;

    UserStatusWithFieldVariable(ExternalUserStatus externalUserStatus) {
        this.externalUserStatus = externalUserStatus;
    }

    public ExternalUserStatus toExternalUserStatus() {
        return externalUserStatus;
    }
}
