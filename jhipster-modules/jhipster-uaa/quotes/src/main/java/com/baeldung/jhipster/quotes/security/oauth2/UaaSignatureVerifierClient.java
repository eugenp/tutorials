package com.baeldung.jhipster.quotes.security.oauth2;

import com.baeldung.jhipster.quotes.config.oauth2.OAuth2Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Client fetching the public key from UAA to create a SignatureVerifier.
 */
@Component
public class UaaSignatureVerifierClient implements OAuth2SignatureVerifierClient {
    private final Logger log = LoggerFactory.getLogger(UaaSignatureVerifierClient.class);
    private final RestTemplate restTemplate;
    protected final OAuth2Properties oAuth2Properties;

    public UaaSignatureVerifierClient(DiscoveryClient discoveryClient, @Qualifier("loadBalancedRestTemplate") RestTemplate restTemplate,
                                  OAuth2Properties oAuth2Properties) {
        this.restTemplate = restTemplate;
        this.oAuth2Properties = oAuth2Properties;
        // Load available UAA servers
        discoveryClient.getServices();
    }

    /**
     * Fetches the public key from the UAA.
     *
     * @return the public key used to verify JWT tokens; or null.
     */
    @Override
    public SignatureVerifier getSignatureVerifier() throws Exception {
        try {
            HttpEntity<Void> request = new HttpEntity<Void>(new HttpHeaders());
            String key = (String) restTemplate
                .exchange(getPublicKeyEndpoint(), HttpMethod.GET, request, Map.class).getBody()
                .get("value");
            return new RsaVerifier(key);
        } catch (IllegalStateException ex) {
            log.warn("could not contact UAA to get public key");
            return null;
        }
    }

    /** Returns the configured endpoint URI to retrieve the public key. */
    private String getPublicKeyEndpoint() {
        String tokenEndpointUrl = oAuth2Properties.getSignatureVerification().getPublicKeyEndpointUri();
        if (tokenEndpointUrl == null) {
            throw new InvalidClientException("no token endpoint configured in application properties");
        }
        return tokenEndpointUrl;
    }
}
