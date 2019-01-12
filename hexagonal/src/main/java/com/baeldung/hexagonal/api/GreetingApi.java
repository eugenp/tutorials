package com.baeldung.hexagonal.api;

import java.util.List;

public interface GreetingApi {
    String sayHello(String nameFirst, String nameLast);

    List<String> listAll();
}
