package com.baeldung.server;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class TweetControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getTweetById_whenDefaultTweetId_thenReturnsDefaultTweet() {
        ResponseEntity<Tweet> response = getTweetById("1");
        assertEquals(OK, response.getStatusCode());
        assertEquals("default tweet", response.getBody()
            .getText());
    }

    @Test
    public void addTweet_whenSendingValidTweet_thenReturnsSuccess() {
        Tweet tweet = Tweet.createTweet("2", "My first sample tweet");
        ResponseEntity<Void> addTweetResponse = restTemplate.postForEntity("/tweet", tweet, Void.class);
        assertEquals(OK, addTweetResponse.getStatusCode());

        ResponseEntity<Tweet> getTweetResponse = getTweetById("2");
        assertEquals(OK, getTweetResponse.getStatusCode());
        assertEquals("2", getTweetResponse.getBody()
            .getId());
        assertEquals("My first sample tweet", getTweetResponse.getBody()
            .getText());
    }

    @Test
    public void addTweet_whenSendingBlankId_thenReturnsError() {
        Tweet tweet = Tweet.createTweet("", "testTweet");
        assertUnProcessedEntity(tweet);
    }

    @Test
    public void addTweet_whenSendingBlankText_thenReturnsError() {
        Tweet tweet = Tweet.createTweet("3", "");
        assertUnProcessedEntity(tweet);
    }

    private ResponseEntity<Tweet> getTweetById(String tweetId) {
        return restTemplate.getForEntity("/tweet/" + tweetId, Tweet.class);
    }

    private void assertUnProcessedEntity(Tweet tweet) {
        ResponseEntity<Void> addTweetResponse = restTemplate.postForEntity("/tweet", tweet, Void.class);
        assertEquals(UNPROCESSABLE_ENTITY, addTweetResponse.getStatusCode());
    }
}
