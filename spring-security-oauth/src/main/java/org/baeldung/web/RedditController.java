package org.baeldung.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class RedditController {

    @Autowired
    private UserDetailsService userDetailsService;

    private OAuth2RestTemplate redditRestTemplate;

    @RequestMapping("/info")
    public String getInfo(Model model) throws Exception {
        String result = redditRestTemplate.getForObject("https://oauth.reddit.com/api/v1/me", String.class);
        JsonNode node = new ObjectMapper().readTree(result);
        String name = node.get("name").asText();
        model.addAttribute("info", name);

        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "reddit";
    }

    @RequestMapping("/reddit/test")
    public String test(Model model) {
        return "test";
    }

    public void setRedditRestTemplate(OAuth2RestTemplate redditRestTemplate) {
        this.redditRestTemplate = redditRestTemplate;
    }

}