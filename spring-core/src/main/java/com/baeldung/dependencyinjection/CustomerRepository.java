package com.baeldung.dependencyinjection;

import java.util.List;

public interface CustomerRepository {

    List<Customer> findAll();
}
