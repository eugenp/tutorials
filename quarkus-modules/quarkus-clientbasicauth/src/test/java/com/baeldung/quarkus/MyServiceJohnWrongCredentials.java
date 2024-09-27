package com.baeldung.quarkus;

import io.quarkus.rest.client.reactive.ClientBasicAuth;

/**
 * Wrong credentials for user "john" to access {@link MyService} 
 */
@ClientBasicAuth(username = "john", password = "wrongPassword")
public interface MyServiceJohnWrongCredentials extends MyService {

}
