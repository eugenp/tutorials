package com.baeldung.scopedvalues.scoped.inheriting;

import com.baeldung.scopedvalues.data.Data;
import com.baeldung.scopedvalues.data.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;
import java.util.random.RandomGenerator;

public class ExternalService {

    public String getData() {
        return "External data";
    }

}
