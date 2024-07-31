package com.baeldung.enums.mapping.user;

import java.util.EnumMap;

public class UserStatusMapper {
    public static EnumMap<UserStatus, ExternalUserStatus> statusesMap;

    static {
        statusesMap = new EnumMap<>(UserStatus.class);
        statusesMap.put(UserStatus.PENDING, ExternalUserStatus.INACTIVE);
        statusesMap.put(UserStatus.BLOCKED, ExternalUserStatus.INACTIVE);
        statusesMap.put(UserStatus.DELETED, ExternalUserStatus.INACTIVE);
        statusesMap.put(UserStatus.INACTIVATED_BY_SYSTEM, ExternalUserStatus.INACTIVE);
        statusesMap.put(UserStatus.ACTIVE, ExternalUserStatus.ACTIVE);
    }

    public static ExternalUserStatus toExternalUserStatus(UserStatus userStatus) {
        return statusesMap.get(userStatus);
    }
}
