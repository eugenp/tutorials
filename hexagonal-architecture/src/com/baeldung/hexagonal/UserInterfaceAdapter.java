package com.baeldung.hexagonal;

public class UserInterfaceAdapter implements UserInterfacePort {

    private UserInterfacePort userService;

    public UserInterfaceAdapter(UserInterfacePort userInterfacePort) {
        this.userService = userInterfacePort;
    }

    public User getUserById(long idUser) {
        return userService.getUserById(idUser);
    }
}
