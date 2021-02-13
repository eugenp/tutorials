package com.baeldung.spring.controller.scribe;

import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("github")
public class GithubController {
    private OAuth20Service createService(String state) {
        return new ServiceBuilder("e1f8d4f1a5c71467a159")
            .apiSecret("4851597541a8f33a4f1bf1c70f3cedcfefbeb13b")
            .state(state)
            .callback("http://localhost:8080/spring-mvc-simple/github/callback")
            .build(GitHubApi.instance());
    }

    @GetMapping(value = "/authorization")
    public RedirectView authorization(HttpServletRequest servletReq) throws InterruptedException, ExecutionException, IOException {
        String state = String.valueOf(new Random().nextInt(999_999));
        OAuth20Service githubService = createService(state);
        servletReq.getSession().setAttribute("state", state);

        String authorizationUrl = githubService.getAuthorizationUrl();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(authorizationUrl);
        return redirectView;
    }

    @GetMapping(value = "/callback", produces = "text/plain")
    @ResponseBody
    public String callback(HttpServletRequest servletReq, @RequestParam("code") String code, @RequestParam("state") String state) throws InterruptedException, ExecutionException, IOException {
        String initialState = (String) servletReq.getSession().getAttribute("state");
        if(initialState.equals(state)) {
            OAuth20Service githubService = createService(initialState);
            OAuth2AccessToken accessToken = githubService.getAccessToken(code);

            OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.github.com/user");
            githubService.signRequest(accessToken, request);
            Response response = githubService.execute(request);

            return response.getBody();
        }
        return "Error";
    }
}
