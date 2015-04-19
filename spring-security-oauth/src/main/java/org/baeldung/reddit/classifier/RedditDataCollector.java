package org.baeldung.reddit.classifier;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.baeldung.reddit.util.UserAgentInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class RedditDataCollector {
    public static final String TRAINING_FILE = "src/main/resources/train.csv";
    public static final String TEST_FILE = "src/main/resources/test.csv";
    public static final int LIMIT = 100;
    public static final Long YEAR = 31536000L;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Long timestamp;
    private final RestTemplate restTemplate;
    private final String subreddit;

    public RedditDataCollector() {
        restTemplate = new RestTemplate();
        final List<ClientHttpRequestInterceptor> list = new ArrayList<ClientHttpRequestInterceptor>();
        list.add(new UserAgentInterceptor());
        restTemplate.setInterceptors(list);
        subreddit = "java";
    }

    public RedditDataCollector(String subreddit) {
        restTemplate = new RestTemplate();
        final List<ClientHttpRequestInterceptor> list = new ArrayList<ClientHttpRequestInterceptor>();
        list.add(new UserAgentInterceptor());
        restTemplate.setInterceptors(list);
        this.subreddit = subreddit;
    }

    public void collectData() {
        final int noOfRounds = 80;
        timestamp = System.currentTimeMillis() / 1000;
        try {
            final FileWriter writer = new FileWriter(TRAINING_FILE);
            for (int i = 0; i < noOfRounds; i++) {
                getPosts(writer);
            }
            writer.close();

            final FileWriter testWriter = new FileWriter(TEST_FILE);
            getPosts(testWriter);
            testWriter.close();
        } catch (final Exception e) {
            logger.error("write to file error", e);
        }
    }

    // ==== Private

    private void getPosts(FileWriter writer) {
        final String fullUrl = "http://www.reddit.com/r/" + subreddit + "/search.json?sort=new&q=timestamp:" + (timestamp - YEAR) + ".." + timestamp + "&restrict_sr=on&syntax=cloudsearch&limit=" + LIMIT;
        try {
            final JsonNode node = restTemplate.getForObject(fullUrl, JsonNode.class);
            parseNode(node, writer);
            Thread.sleep(3000);
        } catch (final Exception e) {
            logger.error("server error", e);
        }

    }

    private void parseNode(JsonNode node, FileWriter writer) throws IOException {
        String line;
        List<String> words;
        int score;
        for (final JsonNode child : node.get("data").get("children")) {
            score = child.get("data").get("score").asInt();
            words = Splitter.onPattern("\\W").omitEmptyStrings().splitToList(child.get("data").get("title").asText());
            timestamp = child.get("data").get("created_utc").asLong();

            line = score + ";";
            line += timestamp + ";";
            line += words.size() + ";" + Joiner.on(' ').join(words) + ";";
            line += child.get("data").get("domain").asText() + "\n";
            writer.write(line);
        }
    }

    public static void main(String[] args) {
        final RedditDataCollector collector = new RedditDataCollector();
        collector.collectData();
    }
}
