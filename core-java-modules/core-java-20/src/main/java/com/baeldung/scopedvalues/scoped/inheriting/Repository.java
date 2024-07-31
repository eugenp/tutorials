package com.baeldung.scopedvalues.scoped.inheriting;

import com.baeldung.scopedvalues.data.Data;
import com.baeldung.scopedvalues.data.User;

import java.util.Optional;

public class Repository {

    public Optional<Data> getData(String id) {
        User loggedInUser = Server.LOGGED_IN_USER.get();
        return loggedInUser.isAdmin()
                ? Optional.of(new Data(id, "Title 1", "Description 1"))
                : Optional.empty();
    }

}
