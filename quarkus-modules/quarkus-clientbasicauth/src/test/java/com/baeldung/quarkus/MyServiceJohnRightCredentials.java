package com.baeldung.quarkus;

import io.quarkus.rest.client.reactive.ClientBasicAuth;

/**
 * Right credentials for user "john" to access {@link MyService} 
 */
@ClientBasicAuth(username = "john", password = "secret1")
public interface MyServiceJohnRightCredentials extends MyService {

}
