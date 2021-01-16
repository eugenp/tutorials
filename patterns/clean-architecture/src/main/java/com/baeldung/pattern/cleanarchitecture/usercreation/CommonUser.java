package com.baeldung.pattern.cleanarchitecture.usercreation;

class CommonUser implements User {

    String name;
    String password;

    CommonUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    CommonUser() {
    }

    @Override
    public boolean passwordIsValid() {
        return password != null && password.length() > 5;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
