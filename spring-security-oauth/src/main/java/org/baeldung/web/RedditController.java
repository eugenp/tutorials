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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
            model.addAttribute("error", e.getMessage());
        }
        return "reddit";
    }

    @RequestMapping("/submit")
    public String submit(Model model, @RequestParam Map<String, String> formParams) {
        try {
            MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
            param.add("api_type", "json");
            param.add("kind", "link");
            param.add("resubmit", "true");
            param.add("sendreplies", "false");
            param.add("then", "comments");

            for (Map.Entry<String, String> entry : formParams.entrySet()) {
                param.add(entry.getKey(), entry.getValue());
            }

            LOGGER.info("User submitting Link with these parameters: " + formParams.entrySet());
            String result = redditRestTemplate.postForObject("https://oauth.reddit.com/api/submit", param, String.class);
            LOGGER.info("Full Reddit Response: " + result);
            String responseMsg = parseResponse(result);
            model.addAttribute("msg", responseMsg);
        } catch (UserApprovalRequiredException e) {
            throw e;
        } catch (UserRedirectRequiredException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error occurred", e);
            model.addAttribute("msg", e.getLocalizedMessage());
        }
        return "submissionResponse";
    }

    @RequestMapping("/post")
    public String showSubmissionForm(Model model) throws JsonProcessingException, IOException {
        try {
            String needsCaptchaResult = needsCaptcha();
            if (needsCaptchaResult.equalsIgnoreCase("true")) {
                String iden = getNewCaptcha();
                model.addAttribute("iden", iden);
            }
        } catch (UserApprovalRequiredException e) {
            throw e;
        } catch (UserRedirectRequiredException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error occurred", e);
            model.addAttribute("error", e.getLocalizedMessage());
            return "reddit";
        }
        return "submissionForm";
    }

    // === private

    private List<String> getSubreddit() throws JsonProcessingException, IOException {
        String result = redditRestTemplate.getForObject("https://oauth.reddit.com/subreddits/popular?limit=50", String.class);
        JsonNode node = new ObjectMapper().readTree(result);
        node = node.get("data").get("children");
        List<String> subreddits = new ArrayList<String>();
        for (JsonNode child : node) {
            subreddits.add(child.get("data").get("display_name").asText());
        }
        return subreddits;
    }

    private String needsCaptcha() {
        String result = redditRestTemplate.getForObject("https://oauth.reddit.com/api/needs_captcha.json", String.class);
        return result;
    }

    private String getNewCaptcha() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity req = new HttpEntity(headers);

        Map<String, String> param = new HashMap<String, String>();
        param.put("api_type", "json");

        ResponseEntity<String> result = redditRestTemplate.postForEntity("https://oauth.reddit.com/api/new_captcha", req, String.class, param);
        String[] split = result.getBody().split("\"");
        return split[split.length - 2];
    }

    private String parseResponse(String responseBody) throws JsonProcessingException, IOException {
        String result = "";
        JsonNode node = new ObjectMapper().readTree(responseBody);
        JsonNode errorNode = node.get("json").get("errors").get(0);
        for (JsonNode child : errorNode) {
            result = result + child.toString().replaceAll("\"|null", "") + "<br>";
        }
        if (result.length() == 0) {
            if (node.get("json").get("data") != null && node.get("json").get("data").get("url") != null)
                return "Post submitted successfully <a href=\"" + node.get("json").get("data").get("url").asText() + "\"> check it out </a>";
            else
                return "Error Occurred";
        }
        return result;
    }

    public void setRedditRestTemplate(OAuth2RestTemplate redditRestTemplate) {
        this.redditRestTemplate = redditRestTemplate;
    }

}
