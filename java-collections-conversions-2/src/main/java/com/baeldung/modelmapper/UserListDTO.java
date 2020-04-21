package com.baeldung.modelmapper;

import java.util.List;

/**
 * UserListDTO class that contain list of username properties
 * @author sasam0320
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
