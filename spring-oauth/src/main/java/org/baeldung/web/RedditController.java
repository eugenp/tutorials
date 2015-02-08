package org.baeldung.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class RedditController {

    private RestOperations redditRestTemplate;

    @RequestMapping("/reddit/info")
    public String getInfo(Model model) throws Exception {
        String result = redditRestTemplate.getForObject("https://oauth.reddit.com/api/v1/me", String.class);
        JsonNode node = new ObjectMapper().readTree(result);
        model.addAttribute("info", node.get("name").asText());
        return "reddit";
    }

    public void setRedditRestTemplate(RestOperations redditRestTemplate) {
        this.redditRestTemplate = redditRestTemplate;
    }

}