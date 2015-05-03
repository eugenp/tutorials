package org.baeldung.web.schedule;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.baeldung.persistence.dao.PostRepository;
import org.baeldung.persistence.model.Post;
import org.baeldung.persistence.model.User;
import org.baeldung.reddit.util.RedditApiConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.JsonNode;

@Component
public class RedditScheduler {

    @Autowired
    @Qualifier("schedulerRedditTemplate")
    private OAuth2RestTemplate redditRestTemplate;

    @Autowired
    private PostRepository postReopsitory;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Scheduled(fixedRate = 1 * 60 * 1000)
    public void schedulePosts() {
        final List<Post> posts = postReopsitory.findBySubmissionDateBeforeAndIsSent(new Date(), false);
        logger.info(posts.size() + " Posts in the queue.");
        for (final Post post : posts) {
            submitPost(post);
        }
    }

    @Scheduled(fixedRate = 3 * 60 * 1000)
    public void checkAndReSubmitPosts() {
        final List<Post> submitted = postReopsitory.findByRedditIDNotNullAndNoOfAttemptsGreaterThan(0);
        logger.info(submitted.size() + " Posts to check their score");
        for (final Post post : submitted) {
            checkAndReSubmit(post);
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
        parseResponse(node, post);
    }

    private void parseResponse(JsonNode node, Post post) {
        final JsonNode errorNode = node.get("json").get("errors").get(0);
        if (errorNode == null) {
            post.setSent(true);
            post.setSubmissionResponse("Successfully sent");
            post.setRedditID(node.get("json").get("data").get("id").asText());
            post.setNoOfAttempts(post.getNoOfAttempts() - 1);
            postReopsitory.save(post);
            logger.info("Successfully sent " + post.toString());
        } else {
            post.setSubmissionResponse(errorNode.toString());
            postReopsitory.save(post);
            logger.info("Error occurred: " + errorNode.toString() + "while submitting post " + post.toString());
        }
    }

    private int getPostScore(String redditId) {
        final JsonNode node = redditRestTemplate.getForObject("https://oauth.reddit.com/api/info?id=t3_" + redditId, JsonNode.class);
        logger.info(node.toString());
        final int score = node.get("data").get("children").get(0).get("data").get("score").asInt();
        logger.info("post score = " + score);
        return score;
    }

    private void deletePost(String redditId) {
        final MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
        param.add("id", "t3_" + redditId);
        final JsonNode node = redditRestTemplate.postForObject("https://oauth.reddit.com/api/del.json", param, JsonNode.class);
        logger.info(node.toString());
    }

    private void checkAndReSubmit(Post post) {
        try {
            checkAndReSubmitInternal(post);
        } catch (final Exception e) {
            logger.error("Error occurred while check post " + post.toString(), e);
        }
    }

    private void checkAndReSubmitInternal(Post post) {
        final long currentTime = new Date().getTime();
        final long interval = currentTime - post.getSubmissionDate().getTime();
        final long intervalInMinutes = TimeUnit.MINUTES.convert(interval, TimeUnit.MILLISECONDS);
        if (intervalInMinutes > post.getTimeInterval()) {
            final int score = getPostScore(post.getRedditID());
            if (score < post.getMinScoreRequired()) {
                deletePost(post.getRedditID());
                post.setRedditID(null);
                post.setSubmissionDate(new Date(currentTime + interval));
                post.setSent(false);
                post.setSubmissionResponse("Not sent yet");
                postReopsitory.save(post);
            } else {
                post.setNoOfAttempts(0);
                postReopsitory.save(post);
            }
        }
    }

    public void setRedditRestTemplate(final OAuth2RestTemplate redditRestTemplate) {
        this.redditRestTemplate = redditRestTemplate;
    }
}
