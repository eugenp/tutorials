package com.baeldung.hexagonal;

public class UserControler {

    private UserInterfacePort userService;

    public void setUserService(UserInterfacePort userService) {
        this.userService = userService;
    }

    public User getUser(long idUser) {
        return userService.getUserById(idUser);
    }
}
