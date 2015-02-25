package org.baeldung.config;

import java.io.Serializable;

import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.RequestEnhancer;
import org.springframework.util.MultiValueMap;

public class CustomRequestEnhancer implements RequestEnhancer, Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void enhance(AccessTokenRequest request, OAuth2ProtectedResourceDetails resource, MultiValueMap<String, String> form, HttpHeaders headers) {
        System.out.println("called===");
        form.set("duration", "permanent");
    }
}
