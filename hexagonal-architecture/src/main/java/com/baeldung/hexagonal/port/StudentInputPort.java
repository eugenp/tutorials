package com.baeldung.hexagonal.port;

public interface StudentInputPort{

    String registerStudent(String name);
    boolean deregisterStudent(String id);

}