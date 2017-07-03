package com.baeldung.threadlocal;

import java.util.UUID;


class UserRepository {
    public String getUserNameForUserId(Integer userId) {
        return UUID.randomUUID().toString();
    }
}
