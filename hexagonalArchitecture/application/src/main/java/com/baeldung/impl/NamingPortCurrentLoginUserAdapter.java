package com.baeldung.impl;

import com.baeldung.service.NamingPort;

public class NamingPortCurrentLoginUserAdapter implements NamingPort {
    @Override
    public String getName() {
        return System.getProperty("user.name");
    }
}
