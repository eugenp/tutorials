package org.baeldung.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.baeldung.persistence.dao.PostRepository;
import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.model.Post;
import org.baeldung.persistence.model.User;
import org.baeldung.reddit.util.RedditApiConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class RedditController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Autowired
    private OAuth2RestTemplate redditRestTemplate;

    @Autowired
    private UserRepository userReopsitory;

    @Autowired
    private PostRepository postReopsitory;

    @RequestMapping("/info")
    public final String getInfo(final Model model) {
        final JsonNode node = redditRestTemplate.getForObject("https://oauth.reddit.com/api/v1/me", JsonNode.class);
        final String name = node.get("name").asText();
        addUser(name, redditRestTemplate.getAccessToken());
        model.addAttribute("info", name);
        return "reddit";
    }

    @RequestMapping("/submit")
    public final String submit(final Model model, @RequestParam final Map<String, String> formParams) {
        final MultiValueMap<String, String> param1 = constructParams(formParams);

        logger.info("Submitting Link with these parameters: " + param1);
        final JsonNode node = redditRestTemplate.postForObject("https://oauth.reddit.com/api/submit", param1, JsonNode.class);
        logger.info("Submitted Link - Full Response from Reddit: " + node.toString());
        final String responseMsg = parseResponse(node);
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

    @RequestMapping("/postSchedule")
    public final String showSchedulePostForm(final Model model) {
        final String needsCaptchaResult = needsCaptcha();
        if (needsCaptchaResult.equalsIgnoreCase("true")) {
            model.addAttribute("msg", "Sorry, You do not have enought karma");
            return "submissionResponse";
        }
        return "schedulePostForm";
    }

    @RequestMapping("/schedule")
    public final String schedule(final Model model, @RequestParam final Map<String, String> formParams) throws ParseException {
        logger.info("User scheduling Post with these parameters: " + formParams.entrySet());
        final User user = userReopsitory.findByAccessToken(redditRestTemplate.getAccessToken().getValue());
        final Post post = new Post();
        post.setUser(user);
        post.setSent(false);
        post.setTitle(formParams.get("title"));
        post.setSubreddit(formParams.get("sr"));
        post.setUrl(formParams.get("url"));
        post.setSubmissionDate(dateFormat.parse(formParams.get("date")));
        post.setSubmissionResponse("Not sent yet");
        if (post.getSubmissionDate().before(new Date())) {
            model.addAttribute("msg", "Invalid date");
            return "submissionResponse";
        }
        postReopsitory.save(post);
        final List<Post> posts = postReopsitory.findByUser(user);
        model.addAttribute("posts", posts);
        return "postListView";
    }

    @RequestMapping("/posts")
    public final String getScheduledPosts(final Model model) {
        final User user = userReopsitory.findByAccessToken(redditRestTemplate.getAccessToken().getValue());
        final List<Post> posts = postReopsitory.findByUser(user);
        model.addAttribute("posts", posts);
        return "postListView";
    }

    // === private

    private final MultiValueMap<String, String> constructParams(final Map<String, String> formParams) {
        final MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
        param.add(RedditApiConstants.API_TYPE, "json");
        param.add(RedditApiConstants.KIND, "link");
        param.add(RedditApiConstants.RESUBMIT, "true");
        param.add(RedditApiConstants.SENDREPLIES, "false");
        param.add(RedditApiConstants.THEN, "comments");
        for (final Map.Entry<String, String> entry : formParams.entrySet()) {
            param.add(entry.getKey(), entry.getValue());
        }
        return param;
    }

    private final Map<String, String> constructParams2(final Map<String, String> formParams) {
        final Map<String, String> param = new HashMap<String, String>();
        param.put(RedditApiConstants.API_TYPE, "json");
        param.put(RedditApiConstants.KIND, "link");
        param.put(RedditApiConstants.RESUBMIT, "true");
        param.put(RedditApiConstants.SENDREPLIES, "false");
        param.put(RedditApiConstants.THEN, "comments");
        for (final Map.Entry<String, String> entry : formParams.entrySet()) {
            param.put(entry.getKey(), entry.getValue());
        }
        return param;
    }

    private final String needsCaptcha() {
        final String result = redditRestTemplate.getForObject("https://oauth.reddit.com/api/needs_captcha.json", String.class);
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
            for (final JsonNode child : errorNode) {
                result = result + child.toString().replaceAll("\"|null", "") + "<br>";
            }
            return result;
        } else {
            if ((node.get("json").get("data") != null) && (node.get("json").get("data").get("url") != null)) {
                return "Post submitted successfully <a href=\"" + node.get("json").get("data").get("url").asText() + "\"> check it out </a>";
            } else {
                return "Error Occurred";
            }
        }
    }

    private final void addUser(final String name, final OAuth2AccessToken token) {
        User user = userReopsitory.findByUsername(name);
        if (user == null) {
            user = new User();
            user.setUsername(name);
            user.setAccessToken(token.getValue());
            user.setRefreshToken(token.getRefreshToken().getValue());
            user.setTokenExpiration(token.getExpiration());
            userReopsitory.save(user);
        }
    }

}
