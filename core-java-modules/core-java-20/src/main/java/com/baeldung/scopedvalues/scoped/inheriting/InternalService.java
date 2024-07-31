package com.baeldung.scopedvalues.scoped.inheriting;

import com.baeldung.scopedvalues.data.Data;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public class InternalService {

    private final Repository repository = new Repository();

    public Optional<Data> getData(HttpServletRequest request) {
        String id = request.getParameter("data_id");
        return repository.getData(id);
    }

}
