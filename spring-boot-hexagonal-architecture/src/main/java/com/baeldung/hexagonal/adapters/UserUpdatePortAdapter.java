package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.ports.UserUpdatePort;

public class UserUpdatePortAdapter implements UserUpdatePort {
    private String pageName;

    @Override
    public void returnIndexPage() {
        pageName = "update-user";
    }

    @Override
    public void showErrors() {
        pageName = "index";
    }

    public String getPageName() {
        return pageName;
    }
}
