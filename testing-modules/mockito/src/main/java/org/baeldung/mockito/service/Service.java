package org.baeldung.mockito.service;

public interface Service {

    void doAction(String request, Callback<Response> callback);

}
