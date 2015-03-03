package org.baeldung.web.schedule;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.baeldung.persistence.dao.PostRepository;
import org.baeldung.persistence.model.Post;
import org.baeldung.persistence.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
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

@EnableScheduling
public class ScheduledTasks {

    private OAuth2RestTemplate redditRestTemplate;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PostRepository postReopsitory;

    @Scheduled(fixedRate = 1 * 60 * 1000)
    public void reportCurrentTime() {
        List<Post> posts = postReopsitory.findBySubmissionDateBeforeAndIsSent(new Date(), false);
        logger.info(posts.size() + " Posts in the queue.");
        for (Post post : posts) {
            submitPost(post);
        }
    }

    private void submitPost(Post post) {
        try {
            User user = post.getUser();
            DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(user.getAccessToken());
            token.setRefreshToken(new DefaultOAuth2RefreshToken((user.getRefreshToken())));
            token.setExpiration(user.getTokenExpiration());
            redditRestTemplate.getOAuth2ClientContext().setAccessToken(token);
            //
            UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(user.getUsername(), token.getValue(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            SecurityContextHolder.getContext().setAuthentication(userAuthToken);
            //

            MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
            param.add("api_type", "json");
            param.add("kind", "link");
            param.add("resubmit", "true");
            param.add("sendreplies", "false");
            param.add("then", "comments");
            param.add("title", post.getTitle());
            param.add("sr", post.getSubreddit());
            param.add("url", post.getUrl());

            logger.info("Submit link with these parameters: " + param.entrySet());
            JsonNode node = redditRestTemplate.postForObject("https://oauth.reddit.com/api/submit", param, JsonNode.class);
            JsonNode errorNode = node.get("json").get("errors").get(0);
            if (errorNode == null) {
                post.setSent(true);
                post.setSubmissionResponse("Successfully sent");
                postReopsitory.save(post);
                logger.info("Successfully sent");
            } else {
                post.setSubmissionResponse(errorNode.toString());
                postReopsitory.save(post);
                logger.info("Error occurred: " + errorNode.toString());
            }
        } catch (Exception e) {
            logger.error("Error occurred", e);
        }
    }

    public void setRedditRestTemplate(OAuth2RestTemplate redditRestTemplate) {
        this.redditRestTemplate = redditRestTemplate;
    }
}