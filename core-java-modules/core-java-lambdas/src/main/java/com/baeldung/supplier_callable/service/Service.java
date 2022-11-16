package com.baeldung.supplier_callable.service;

import com.baeldung.supplier_callable.data.User;

public interface Service {

    User execute(User user);
}
