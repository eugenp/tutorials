package org.baeldung.web.schedule;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.baeldung.persistence.dao.PostRepository;
import org.baeldung.persistence.model.Post;
import org.baeldung.persistence.model.User;
import org.baeldung.reddit.util.RedditApiConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.JsonNode;

public class ScheduledTasks {

    private OAuth2RestTemplate redditRestTemplate;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PostRepository postReopsitory;

    @Scheduled(fixedRate = 1 * 60 * 1000)
    public void reportCurrentTime() {
        final List<Post> posts = postReopsitory.findBySubmissionDateBeforeAndIsSent(new Date(), false);
        logger.info(posts.size() + " Posts in the queue.");
        for (final Post post : posts) {
            submitPost(post);
        }
    }

    private void submitPost(final Post post) {
        try {
            submitPostInternal(post);
        } catch (final Exception e) {
            logger.error("Error occurred while submitting post " + post.toString(), e);
        }
    }

    private void submitPostInternal(final Post post) {
        final User user = post.getUser();
        final DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(user.getAccessToken());
        token.setRefreshToken(new DefaultOAuth2RefreshToken((user.getRefreshToken())));
        token.setExpiration(user.getTokenExpiration());
        redditRestTemplate.getOAuth2ClientContext().setAccessToken(token);
        //
        final UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(user.getUsername(), token.getValue(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(userAuthToken);
        //
        final MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
        param.add(RedditApiConstants.TITLE, post.getTitle());
        param.add(RedditApiConstants.SR, post.getSubreddit());
        param.add(RedditApiConstants.URL, post.getUrl());
        param.add(RedditApiConstants.API_TYPE, "json");
        param.add(RedditApiConstants.KIND, "link");
        param.add(RedditApiConstants.RESUBMIT, "true");
        param.add(RedditApiConstants.THEN, "comments");
        if (post.isSendReplies()) {
            param.add(RedditApiConstants.SENDREPLIES, "true");
        }

        logger.info("Submit link with these parameters: " + param.entrySet());
        final JsonNode node = redditRestTemplate.postForObject("https://oauth.reddit.com/api/submit", param, JsonNode.class);
        final JsonNode errorNode = node.get("json").get("errors").get(0);
        if (errorNode == null) {
            post.setSent(true);
            post.setSubmissionResponse("Successfully sent");
            postReopsitory.save(post);
            logger.info("Successfully sent " + post.toString());
        } else {
            post.setSubmissionResponse(errorNode.toString());
            postReopsitory.save(post);
            logger.info("Error occurred: " + errorNode.toString() + "while submitting post " + post.toString());
        }
    }

    public void setRedditRestTemplate(final OAuth2RestTemplate redditRestTemplate) {
        this.redditRestTemplate = redditRestTemplate;
    }
}