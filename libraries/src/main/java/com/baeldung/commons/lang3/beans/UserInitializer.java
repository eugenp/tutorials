package com.baeldung.commons.lang3.beans;

import org.apache.commons.lang3.concurrent.LazyInitializer;

public class UserInitializer extends LazyInitializer<User> {

    @Override
    protected User initialize() {
        return new User("John", "john@domain.com");
    }
}
