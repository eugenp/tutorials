package com.baeldung.sealed.interfaces;

public sealed interface Person permits Employee, Customer {

    String getFirstName();
    String getLastName();

}
