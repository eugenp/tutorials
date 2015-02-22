package org.baeldung.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.UserApprovalRequiredException;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    @RequestMapping("/submit")
    public String submit(Model model, @RequestParam Map<String, String> formParams) {
        try {
            System.out.println(formParams.keySet());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity req = new HttpEntity(headers);

            Map<String, String> param = new HashMap<String, String>();
            param.put("api_type", "json");
            param.put("kind", "self");
            param.put("sr", "api");
            // param.put("iden", "XCzyTdJveIcYXNhLJ4a2X9WVDswtx83u");
            // param.put("captcha", "BJMGMU");
            // param.put("title", "http2 is coming soon");
            // param.put("text", "http2 is coming soon what do you think about that");
            param.putAll(formParams);

            System.out.println(param.keySet());
            System.out.println(param.entrySet());
            ResponseEntity<String> result = redditRestTemplate.postForEntity("https://oauth.reddit.com/api/submit", req, String.class, param);
            model.addAttribute("error", result.getBody());
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

    @RequestMapping("/post")
    public String showSubmissionForm(Model model) throws JsonProcessingException, IOException {
        String needsCaptchaResult = needsCaptcha();
        if (needsCaptchaResult.equalsIgnoreCase("true")) {
            String newCaptchaResult = getNewCaptcha();
            String[] split = newCaptchaResult.split("\"");
            String iden = split[split.length - 2];
            model.addAttribute("iden", iden.trim());
        }
        return "submissionForm";
    }

    //

    public List<String> getSubreddit(Model model) throws JsonProcessingException, IOException {
        String result = redditRestTemplate.getForObject("https://oauth.reddit.com/subreddits/popular", String.class);
        JsonNode node = new ObjectMapper().readTree(result);
        node = node.get("data").get("children");
        List<String> subreddits = new ArrayList<String>();
        for (JsonNode child : node) {
            subreddits.add(child.get("data").get("display_name").asText());
        }
        return subreddits;
    }

    public String needsCaptcha() {
        String result = redditRestTemplate.getForObject("https://oauth.reddit.com/api/needs_captcha.json", String.class);
        return result;
    }

    public String getNewCaptcha() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity req = new HttpEntity(headers);

        Map<String, String> param = new HashMap<String, String>();
        param.put("api_type", "json");

        ResponseEntity<String> result = redditRestTemplate.postForEntity("https://oauth.reddit.com/api/new_captcha", req, String.class, param);
        return result.getBody();
    }

    public void setRedditRestTemplate(OAuth2RestTemplate redditRestTemplate) {
        this.redditRestTemplate = redditRestTemplate;
    }

}