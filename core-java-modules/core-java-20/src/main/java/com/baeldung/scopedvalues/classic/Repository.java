package com.baeldung.scopedvalues.classic;

import com.baeldung.scopedvalues.data.Data;
import com.baeldung.scopedvalues.data.User;

import java.util.Optional;

public class Repository {

    public Optional<Data> getData(String id, User user) {
        return user.isAdmin()
                ? Optional.of(new Data(id, "Title 1", "Description 1"))
                : Optional.empty();
    }

}
