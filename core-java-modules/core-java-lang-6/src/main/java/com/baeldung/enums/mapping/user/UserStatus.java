package com.baeldung.enums.mapping.user;

public enum UserStatus {
    PENDING, ACTIVE, BLOCKED, INACTIVATED_BY_SYSTEM, DELETED;

    public ExternalUserStatus toExternalUserStatusViaSwitchStatement() {
        return switch (this) {
            case PENDING, BLOCKED, INACTIVATED_BY_SYSTEM, DELETED -> ExternalUserStatus.INACTIVE;
            case ACTIVE -> ExternalUserStatus.ACTIVE;
        };
    }

    public ExternalUserStatus toExternalUserStatusViaRegularSwitch() {
        switch (this) {
        case PENDING:
        case BLOCKED:
        case INACTIVATED_BY_SYSTEM:
        case DELETED:
            return ExternalUserStatus.INACTIVE;
        case ACTIVE:
            return ExternalUserStatus.ACTIVE;
        }
        return null;
    }

}
