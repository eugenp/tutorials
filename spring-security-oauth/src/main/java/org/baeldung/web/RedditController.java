package org.baeldung.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.UserApprovalRequiredException;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class RedditController {
    private OAuth2RestTemplate redditRestTemplate;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @RequestMapping("/info")
    public String getInfo(Model model) {
        try {
            String result = redditRestTemplate.getForObject("https://oauth.reddit.com/api/v1/me", String.class);
            JsonNode node = new ObjectMapper().readTree(result);
            String name = node.get("name").asText();
            model.addAttribute("info", name);
        } catch (UserApprovalRequiredException e) {
            throw e;
        } catch (UserRedirectRequiredException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error occurred", e);
            model.addAttribute("error", e.getLocalizedMessage());
        }
        return "reddit";
    }

    public void setRedditRestTemplate(OAuth2RestTemplate redditRestTemplate) {
        this.redditRestTemplate = redditRestTemplate;
    }

}