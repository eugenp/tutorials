package com.baeldung.impl;

import com.baeldung.service.NamingPort;

public class NamingPortStaticAdapter implements NamingPort {
    @Override
    public String getName() {
        return "World";
    }
}
