package com.baeldung.auth0.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.SessionUtils;
import com.auth0.Tokens;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baeldung.auth0.AuthConfig;

@Controller
public class AuthController {

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private AuthConfig config;

    private static final String auth0TokenUrl = "https://dev-ansh.auth0.com/oauth/token";

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    protected String login(HttpServletRequest request, HttpServletResponse response) {
        String redirectUri = config.getContextPath(request) + "/callback";
        String authorizeUrl = authenticationController.buildAuthorizeUrl(request, response, redirectUri)
            .withScope("openid email")
            .build();
        return "redirect:" + authorizeUrl;
    }

    @RequestMapping(value="/callback")
    public String callback(HttpServletRequest request, HttpServletResponse response) throws IOException, IdentityVerificationException {
        Tokens tokens = authenticationController.handle(request, response);
        SessionUtils.set(request, "accessToken", tokens.getAccessToken());
        SessionUtils.set(request, "idToken", tokens.getIdToken());      

        DecodedJWT jwt = JWT.decode(tokens.getIdToken());
        String email = jwt.getClaims().get("email").asString();

        SessionUtils.set(request, "email", email); 

        return "redirect:" + config.getContextPath(request) + "/"; 
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest req) {
        if (req.getSession() != null) {
            req.getSession().invalidate();
        }
        String returnTo = config.getContextPath(req);
        String logoutUrl = config.getLogoutUrl() + "?client_id=" + config.getClientId() + "&returnTo=" +returnTo;
        return "redirect:" + logoutUrl;
    }

    public String getManagementApiToken() {
    
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    
        JSONObject requestBody = new JSONObject();
    
        requestBody.put("client_id", config.getManagementApiClientId());
        requestBody.put("client_secret", config.getManagementApiClientSecret());
        requestBody.put("audience", "https://dev-ansh.auth0.com/api/v2/");
        requestBody.put("grant_type", config.getGrantType()); 
    
        HttpEntity<String> request = new HttpEntity<String>(requestBody.toString(), headers);
    
        RestTemplate restTemplate = new RestTemplate();
    
        @SuppressWarnings("unchecked")
        HashMap<String, String> result = restTemplate.postForObject(auth0TokenUrl, request, HashMap.class);
    
        return result.get("access_token");
    }


}
