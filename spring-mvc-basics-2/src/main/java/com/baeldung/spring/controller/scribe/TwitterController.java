package com.baeldung.spring.controller.scribe;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuth10aService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("twitter")
public class TwitterController {

    private OAuth10aService createService() {
        return new ServiceBuilder("PSRszoHhRDVhyo2RIkThEbWko")
            .apiSecret("prpJbz03DcGRN46sb4ucdSYtVxG8unUKhcnu3an5ItXbEOuenL")
            .callback("http://localhost:8080/spring-mvc-simple/twitter/callback")
            .build(TwitterApi.instance());
    }

    @GetMapping(value = "/authorization")
    public RedirectView authorization(HttpServletRequest servletReq) throws InterruptedException, ExecutionException, IOException {
        OAuth10aService twitterService = createService();

        OAuth1RequestToken requestToken = twitterService.getRequestToken();
        String authorizationUrl = twitterService.getAuthorizationUrl(requestToken);
        servletReq.getSession().setAttribute("requestToken", requestToken);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(authorizationUrl);
        return redirectView;
    }

    @GetMapping(value = "/callback", produces = "text/plain")
    @ResponseBody
    public String callback(HttpServletRequest servletReq, @RequestParam("oauth_verifier") String oauthV) throws InterruptedException, ExecutionException, IOException {
        OAuth10aService twitterService = createService();
        OAuth1RequestToken requestToken = (OAuth1RequestToken) servletReq.getSession().getAttribute("requestToken");
        OAuth1AccessToken accessToken = twitterService.getAccessToken(requestToken, oauthV);

        OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/account/verify_credentials.json");
        twitterService.signRequest(accessToken, request);
        Response response = twitterService.execute(request);

        return response.getBody();
    }
}
