package com.baeldung.scopedvalues.classic;

import com.baeldung.scopedvalues.data.Data;
import com.baeldung.scopedvalues.data.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class Service {

    private final Repository repository = new Repository();

    public Optional<Data> getData(HttpServletRequest request, User loggedInUser) {
        String id = request.getParameter("data_id");
        return repository.getData(id, loggedInUser);
    }

}
