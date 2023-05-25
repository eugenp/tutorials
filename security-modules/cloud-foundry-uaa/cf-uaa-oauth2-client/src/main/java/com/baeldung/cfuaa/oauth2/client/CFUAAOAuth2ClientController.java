package com.baeldung.cfuaa.oauth2.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class CFUAAOAuth2ClientController {

    @Value("${resource.server.url}")
    private String remoteResourceServer;

    private RestTemplate restTemplate;

    private OAuth2AuthorizedClientService authorizedClientService;

    public CFUAAOAuth2ClientController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
        this.restTemplate = new RestTemplate();
    }

    @RequestMapping("/")
    public String index(OAuth2AuthenticationToken authenticationToken) {
        OAuth2AuthorizedClient oAuth2AuthorizedClient = this.authorizedClientService.loadAuthorizedClient(authenticationToken.getAuthorizedClientRegistrationId(), authenticationToken.getName());
        OAuth2AccessToken oAuth2AccessToken = oAuth2AuthorizedClient.getAccessToken();

        String response = "Hello, " + authenticationToken.getPrincipal().getName();
        response += "</br></br>";
        response += "Here is your accees token :</br>" + oAuth2AccessToken.getTokenValue();
        response += "</br>";
        response += "</br>You can use it to call these Resource Server APIs:";
        response += "</br></br>";
        response += "<a href='/read'>Call Resource Server Read API</a>";
        response += "</br>";
        response += "<a href='/write'>Call Resource Server Write API</a>";
        return response;
    }

    @RequestMapping("/read")
    public String read(OAuth2AuthenticationToken authenticationToken) {
        String url = remoteResourceServer + "/read";
        return callResourceServer(authenticationToken, url);
    }

    @RequestMapping("/write")
    public String write(OAuth2AuthenticationToken authenticationToken) {
        String url = remoteResourceServer + "/write";
        return callResourceServer(authenticationToken, url);
    }

    private String callResourceServer(OAuth2AuthenticationToken authenticationToken, String url) {
        OAuth2AuthorizedClient oAuth2AuthorizedClient = this.authorizedClientService.loadAuthorizedClient(authenticationToken.getAuthorizedClientRegistrationId(), authenticationToken.getName());
        OAuth2AccessToken oAuth2AccessToken = oAuth2AuthorizedClient.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oAuth2AccessToken.getTokenValue());

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> responseEntity = null;

        String response = null;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            response = responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            response = e.getMessage();
        }
        return response;
    }
}