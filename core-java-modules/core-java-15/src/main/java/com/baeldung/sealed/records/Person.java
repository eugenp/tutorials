package com.baeldung.sealed.records;

public sealed interface Person permits Customer, Employee {

    String getSalutation();
    String getId();

}
