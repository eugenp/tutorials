package com.baeldung.security.manager;

import java.security.BasicPermission;

public class CustomPermission extends BasicPermission {
    public CustomPermission(String name) {
        super(name);
    }

    public CustomPermission(String name, String actions) {
        super(name, actions);
    }
}
