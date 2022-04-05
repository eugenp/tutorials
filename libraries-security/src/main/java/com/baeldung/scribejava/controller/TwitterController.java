package com.baeldung.scribejava.controller;

import com.baeldung.scribejava.service.TwitterService;
import com.github.scribejava.core.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

@RestController
public class TwitterController {

    @Autowired
    private TwitterService service;


    @GetMapping(value ="/me/twitter")
    public String me(HttpServletResponse servletResponse){
        try {
            OAuth1RequestToken requestToken = service.getService().getRequestToken();

            String auth = service.getService().getAuthorizationUrl(requestToken);

            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + auth);
            } catch (IOException e) {
                servletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return null;
            }

            System.out.println("Insert twitter code:");
            Scanner in = new Scanner(System.in);

            String oauthverifier = in.nextLine();

            final OAuth1AccessToken accessToken = service.getService().getAccessToken(requestToken,oauthverifier);

            OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/account/verify_credentials.json");
            service.getService().signRequest(accessToken, request);
            Response response = service.getService().execute(request);
            return response.getBody();

        } catch (IOException | InterruptedException | ExecutionException e) {
            servletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return null;
    }



}
