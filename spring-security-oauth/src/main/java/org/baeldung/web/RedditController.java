package org.baeldung.web;

import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class RedditController {

    private OAuth2RestTemplate redditRestTemplate;

    @RequestMapping("/info")
    public String getInfo(Model model) throws Exception {
        String result = redditRestTemplate.getForObject("https://oauth.reddit.com/api/v1/me", String.class);
        JsonNode node = new ObjectMapper().readTree(result);
        String name = node.get("name").asText();
        model.addAttribute("info", name);
        return "reddit";
    }

    public void setRedditRestTemplate(OAuth2RestTemplate redditRestTemplate) {
        this.redditRestTemplate = redditRestTemplate;
    }

}