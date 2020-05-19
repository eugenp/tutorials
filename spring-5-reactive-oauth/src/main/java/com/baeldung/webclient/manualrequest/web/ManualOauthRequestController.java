package com.baeldung.webclient.manualrequest.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.nimbusds.oauth2.sdk.GrantType;

import reactor.core.publisher.Mono;

@RestController
public class ManualOauthRequestController {

    private static Logger logger = LoggerFactory.getLogger(ManualOauthRequestController.class);

    private static final String RESOURCE_ENDPOINT = "localhost:8082/spring-security-oauth-resource/foos/1";
    
    @Value("${the.authorization.client-id}")
    private String clientId;
    
    @Value("${the.authorization.client-secret}")
    private String clientSecret;
    
    @Value("${the.authorization.token-uri}")
    private String tokenUri;
    
    @Autowired
    WebClient client;

    @GetMapping("/manual-request-oauth")
    public Mono<String> obtainSecuredResource() {
        logger.info("Creating web client...");
        Mono<String> resource = client.post()
            .uri(tokenUri)
            .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString((clientId + ":" + clientSecret).getBytes()))
            .body(BodyInserters.fromFormData(OAuth2ParameterNames.GRANT_TYPE, GrantType.CLIENT_CREDENTIALS.getValue()))
            .retrieve()
            .bodyToMono(JsonNode.class)
            .flatMap(tokenResponse -> {
                String accessTokenValue = tokenResponse.get("access_token")
                    .textValue();
                logger.info("Retrieved the following access token: {}", accessTokenValue);
                return client.get()
                    .uri(RESOURCE_ENDPOINT)
                    .headers(h -> h.setBearerAuth(accessTokenValue))
                    .retrieve()
                    .bodyToMono(String.class);
            });
        logger.info("non-blocking Oauth calls registered...");
        return resource.map(res -> "Retrieved the resource using a manual approach: " + res);

    }

}
