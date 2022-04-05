package com.baeldung.scribejava.controller;

import com.baeldung.scribejava.service.MyService;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController(value = "/user")
public class UserController {

    @Autowired
    private MyService service;

    @GetMapping("/me/myapi")
    public String me(@RequestParam String username, @RequestParam String password, HttpServletResponse responsehttp) {

        try {
            OAuth2AccessToken token = service.getService().getAccessTokenPasswordGrant(username, password);

            OAuthRequest request = new OAuthRequest(Verb.GET, "http://localhost:8080/me");
            service.getService().signRequest(token, request);
            Response response = service.getService().execute(request);

            return response.getBody();

        } catch (Exception e) {
            responsehttp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return null;

    }

    @GetMapping("/me")
    public Principal user(Principal principal) {
        return principal;
    }
}
