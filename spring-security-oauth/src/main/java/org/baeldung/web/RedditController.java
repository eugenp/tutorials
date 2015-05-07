package org.baeldung.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.baeldung.persistence.dao.PostRepository;
import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.model.Post;
import org.baeldung.persistence.model.User;
import org.baeldung.reddit.classifier.RedditClassifier;
import org.baeldung.reddit.util.RedditApiConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class RedditController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private final SimpleDateFormat dfHour = new SimpleDateFormat("HH");
    public static final String REMEMBER_ME_COOKIE = "CustomRememberMe";

    @Autowired
    @Qualifier("redditRestTemplate")
    private OAuth2RestTemplate redditRestTemplate;

    @Autowired
    private UserRepository userReopsitory;

    @Autowired
    private PostRepository postReopsitory;

    @Autowired
    private RedditClassifier redditClassifier;

    @RequestMapping("/login")
    public final String redditLogin(@CookieValue(value = REMEMBER_ME_COOKIE, required = false) String rememberMe, HttpServletRequest request, HttpServletResponse response) {
        if (!canAutoLogin(rememberMe)) {
            final JsonNode node = redditRestTemplate.getForObject("https://oauth.reddit.com/api/v1/me", JsonNode.class);
            loadAuthentication(node.get("name").asText(), redditRestTemplate.getAccessToken(), response);
        }
        return "redirect:home.html";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
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
        final boolean isCaptchaNeeded = getCurrentUser().isCaptchaNeeded();
        if (isCaptchaNeeded) {
            final String iden = getNewCaptcha();
            model.addAttribute("iden", iden);
        }
        return "submissionForm";
    }

    @RequestMapping("/postSchedule")
    public final String showSchedulePostForm(final Model model) {
        final boolean isCaptchaNeeded = getCurrentUser().isCaptchaNeeded();
        if (isCaptchaNeeded) {
            model.addAttribute("msg", "Sorry, You do not have enought karma");
            return "submissionResponse";
        }
        return "schedulePostForm";
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.POST)
    public final String schedule(final Model model, @RequestParam final Map<String, String> formParams) throws ParseException {
        logger.info("User scheduling Post with these parameters: " + formParams.entrySet());
        final User user = getCurrentUser();
        final Post post = new Post();
        post.setUser(user);
        post.setSent(false);
        post.setTitle(formParams.get("title"));
        post.setSubreddit(formParams.get("sr"));
        post.setUrl(formParams.get("url"));
        post.setNoOfAttempts(Integer.parseInt(formParams.get("attempt")));
        post.setTimeInterval(Integer.parseInt(formParams.get("interval")));
        post.setMinScoreRequired(Integer.parseInt(formParams.get("score")));

        if (formParams.containsKey("sendreplies")) {
            post.setSendReplies(true);
        }
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
        final User user = getCurrentUser();
        final List<Post> posts = postReopsitory.findByUser(user);
        model.addAttribute("posts", posts);
        return "postListView";
    }

    @RequestMapping(value = "/predicatePostResponse", method = RequestMethod.POST)
    @ResponseBody
    public final String predicatePostResponse(@RequestParam(value = "title") final String title, @RequestParam(value = "domain") final String domain) {
        final int hour = Integer.parseInt(dfHour.format(new Date()));
        final int result = redditClassifier.classify(redditClassifier.convertPost(title, domain, hour));
        return (result == RedditClassifier.GOOD) ? "{Good Response}" : "{Bad response}";
    }

    // === post actions

    @RequestMapping(value = "/deletePost/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@PathVariable("id") final Long id) {
        postReopsitory.delete(id);
    }

    @RequestMapping(value = "/editPost/{id}", method = RequestMethod.GET)
    public String showEditPostForm(final Model model, @PathVariable Long id) {
        final Post post = postReopsitory.findOne(id);
        model.addAttribute("post", post);
        model.addAttribute("dateValue", dateFormat.format(post.getSubmissionDate()));
        return "editPostForm";
    }

    @RequestMapping(value = "/updatePost/{id}", method = RequestMethod.POST)
    public String updatePost(Model model, @PathVariable("id") final Long id, @RequestParam final Map<String, String> formParams) throws ParseException {
        final Post post = postReopsitory.findOne(id);
        post.setTitle(formParams.get("title"));
        post.setSubreddit(formParams.get("sr"));
        post.setUrl(formParams.get("url"));
        if (formParams.containsKey("sendreplies")) {
            post.setSendReplies(true);
        } else {
            post.setSendReplies(false);
        }
        post.setSubmissionDate(dateFormat.parse(formParams.get("date")));
        if (post.getSubmissionDate().before(new Date())) {
            model.addAttribute("msg", "Invalid date");
            return "submissionResponse";
        }
        postReopsitory.save(post);
        return "redirect:/posts";
    }

    // === private

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private final MultiValueMap<String, String> constructParams(final Map<String, String> formParams) {
        final MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
        param.add(RedditApiConstants.API_TYPE, "json");
        param.add(RedditApiConstants.KIND, "link");
        param.add(RedditApiConstants.RESUBMIT, "true");
        param.add(RedditApiConstants.THEN, "comments");
        for (final Map.Entry<String, String> entry : formParams.entrySet()) {
            param.add(entry.getKey(), entry.getValue());
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

    private void loadAuthentication(final String name, final OAuth2AccessToken token, HttpServletResponse response) {
        User user = userReopsitory.findByUsername(name);
        if (user == null) {
            user = new User();
            user.setUsername(name);
        }

        if (needsCaptcha().equalsIgnoreCase("true")) {
            user.setNeedCaptcha(true);
        } else {
            user.setNeedCaptcha(false);
        }

        user.setAccessToken(token.getValue());
        user.setRefreshToken(token.getRefreshToken().getValue());
        user.setTokenExpiration(token.getExpiration());
        userReopsitory.save(user);

        generateRememberMeToken(user, response);

        final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, token.getValue(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private void generateRememberMeToken(User user, HttpServletResponse response) {
        String rememberMe = RandomStringUtils.randomAlphanumeric(30);
        while (userReopsitory.findByRememberMeToken(rememberMe) != null) {
            rememberMe = RandomStringUtils.randomAlphanumeric(30);
        }
        user.setRememberMeToken(rememberMe);
        userReopsitory.save(user);
        final Cookie c = new Cookie(REMEMBER_ME_COOKIE, rememberMe);
        c.setMaxAge(1209600);
        response.addCookie(c);
    }

    private boolean canAutoLogin(String rememberMeToken) {
        if (rememberMeToken != null) {
            final User user = userReopsitory.findByRememberMeToken(rememberMeToken);
            if (user != null) {
                logger.info("Auto Login successfully");
                final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, user.getAccessToken(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
                SecurityContextHolder.getContext().setAuthentication(auth);
                return true;
            }
        }
        return false;
    }

}
