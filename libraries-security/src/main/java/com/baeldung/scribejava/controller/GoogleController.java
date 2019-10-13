package com.baeldung.scribejava.controller;

import com.baeldung.scribejava.service.GoogleService;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class GoogleController {

    @Autowired
    private GoogleService service;


    @GetMapping(value ="/me/google")
    public void me(HttpServletResponse response){
        String auth = service.getService().getAuthorizationUrl();

        response.setHeader("Location", auth);
        response.setStatus(302);

    }

    @GetMapping(value = "/auth/google")
    public String google(@RequestParam String code, HttpServletResponse servletResponse){

        try {
            OAuth2AccessToken token = service.getService().getAccessToken(code);

            OAuthRequest request = new OAuthRequest(Verb.GET, "https://www.googleapis.com/oauth2/v1/userinfo?alt=json");
            service.getService().signRequest(token, request);
            Response response = service.getService().execute(request);
            return response.getBody();

        }catch (Exception e){
            servletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return null;
    }

}
