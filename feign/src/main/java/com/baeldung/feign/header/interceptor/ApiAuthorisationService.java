package com.baeldung.feign.header.interceptor;

import java.util.UUID;

public class ApiAuthorisationService implements AuthorisationService {

    public String getAuthToken() {
        return UUID.randomUUID()
	        .toString();
    }
	
}
