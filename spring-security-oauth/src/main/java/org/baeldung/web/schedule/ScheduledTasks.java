package org.baeldung.web.schedule;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.baeldung.persistence.dao.PostRepository;
import org.baeldung.persistence.model.Post;
import org.baeldung.persistence.model.User;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

@EnableScheduling
public class ScheduledTasks {

    private OAuth2RestTemplate redditRestTemplate;

    @Autowired
    private PostRepository postReopsitory;

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void reportCurrentTime() throws JsonProcessingException, IOException, ParseException {
        List<Post> posts = postReopsitory.findBySubmissionDateBefore(new Date());
        System.out.println(posts.size());
        for (Post post : posts) {
            if (post.isSent())
                continue;
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

            JsonNode node = redditRestTemplate.postForObject("https://oauth.reddit.com/api/submit", param, JsonNode.class);
            JsonNode errorNode = node.get("json").get("errors").get(0);
            if (errorNode == null) {
                post.setSent(true);
                postReopsitory.save(post);
            } else {
                post.setSubmissionResponse(errorNode.toString());
                postReopsitory.save(post);
            }
        }
    }

    public void setRedditRestTemplate(OAuth2RestTemplate redditRestTemplate) {
        this.redditRestTemplate = redditRestTemplate;
    }
}