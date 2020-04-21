package com.baeldung.modelmapper;

import java.util.List;

/**
 * @author sasam0320
 * @description UserListDTO class that contain list of username properties
 */
public class UserListDTO {

    private List<String> usernames;

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }
}
