package org.baeldung.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
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

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OAuth2RestTemplate redditRestTemplate;

    // API

    @RequestMapping("/info")
    public final String getInfo(Model model) {
        final JsonNode node = redditRestTemplate.getForObject("https://oauth.reddit.com/api/v1/me", JsonNode.class);
        final String name = node.get("name").asText();
        model.addAttribute("info", name);
        return "reddit";
    }

    @RequestMapping("/submit")
    public final String submit(Model model, @RequestParam Map<String, String> formParams) {
        final MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
        param.add("api_type", "json");
        param.add("kind", "link");
        param.add("resubmit", "true");
        param.add("sendreplies", "false");
        param.add("then", "comments");
        for (final Map.Entry<String, String> entry : formParams.entrySet()) {
            param.add(entry.getKey(), entry.getValue());
        }

        logger.info("User submitting Link with these parameters: " + formParams.entrySet());
        JsonNode node = redditRestTemplate.postForObject("https://oauth.reddit.com/api/submit", param, JsonNode.class);
        logger.info("Full Reddit Response: " + node.toString());
        String responseMsg = parseResponse(node);
        model.addAttribute("msg", responseMsg);
        return "submissionResponse";
    }

    @RequestMapping("/post")
    public final String showSubmissionForm(final Model model) {
        final String needsCaptchaResult = needsCaptcha();
        if (needsCaptchaResult.equalsIgnoreCase("true")) {
            final String iden = getNewCaptcha();
            model.addAttribute("iden", iden);
        }
        return "submissionForm";
    }

    // === private

    final List<String> getSubreddit() throws JsonProcessingException, IOException {
        final String result = redditRestTemplate.getForObject("https://oauth.reddit.com/subreddits/popular?limit=50", String.class);
        final JsonNode node = new ObjectMapper().readTree(result).get("data").get("children");
        final List<String> subreddits = new ArrayList<String>();
        for (JsonNode child : node) {
            subreddits.add(child.get("data").get("display_name").asText());
        }
        return subreddits;
    }

    private final String needsCaptcha() {
        String result = redditRestTemplate.getForObject("https://oauth.reddit.com/api/needs_captcha.json", String.class);
        return result;
    }

    private final String getNewCaptcha() {
        final Map<String, String> param = new HashMap<String, String>();
        param.put("api_type", "json");

        final String result = redditRestTemplate.postForObject("https://oauth.reddit.com/api/new_captcha", param, String.class, param);
        final String[] split = result.split("\"");
        return split[split.length - 2];
    }

    private final String parseResponse(final JsonNode node) {
        String result = "";
        final JsonNode errorNode = node.get("json").get("errors").get(0);
        if (errorNode != null) {
            for (JsonNode child : errorNode) {
                result = result + child.toString().replaceAll("\"|null", "") + "<br>";
            }
            return result;
        } else {
            if (node.get("json").get("data") != null && node.get("json").get("data").get("url") != null)
                return "Post submitted successfully <a href=\"" + node.get("json").get("data").get("url").asText() + "\"> check it out </a>";
            else
                return "Error Occurred";
        }
    }

}
