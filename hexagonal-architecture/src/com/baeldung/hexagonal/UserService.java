package com.baeldung.hexagonal;

public class UserService implements UserInterfacePort {

    private UserDataBasePort userDataBasePort;

    public void setUserDataBasePort(UserDataBasePort userDataBaseAdapter) {
        this.userDataBasePort = userDataBaseAdapter;
    }

    public User getUserById(long idUser) {
        return userDataBasePort.getUserById(idUser);
    }
}
