package com.baeldung.dependencyinjection;

public interface CustomerRepository {

    Customer findById(Long id);
}
