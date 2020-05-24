package com.baeldung.auth0.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

@Controller
public class HomeController {
    
    @GetMapping(value = "/")
    @ResponseBody
    public String home(HttpServletRequest request, HttpServletResponse response, final Authentication authentication) throws IOException {
        
        if (authentication!= null && authentication instanceof TestingAuthenticationToken) {
            TestingAuthenticationToken token = (TestingAuthenticationToken) authentication;
            
            DecodedJWT jwt = JWT.decode(token.getCredentials().toString());
            String email = jwt.getClaims().get("email").asString();
            
            return "Welcome, " + email + "!";
        } else {
            response.sendRedirect("http://localhost:8080/login");
            return null;
        }
    }

}
