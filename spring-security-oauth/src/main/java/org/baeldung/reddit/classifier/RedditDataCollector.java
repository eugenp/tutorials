package org.baeldung.reddit.classifier;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String postAfter;
    private final RestTemplate restTemplate;
    private final String subreddit;
    private final int minScore;

    public RedditDataCollector() {
        restTemplate = new RestTemplate();
        final List<ClientHttpRequestInterceptor> list = new ArrayList<ClientHttpRequestInterceptor>();
        list.add(new UserAgentInterceptor());
        restTemplate.setInterceptors(list);
        subreddit = "all";
        minScore = 4;
    }

    public RedditDataCollector(String subreddit, int minScore) {
        restTemplate = new RestTemplate();
        final List<ClientHttpRequestInterceptor> list = new ArrayList<ClientHttpRequestInterceptor>();
        list.add(new UserAgentInterceptor());
        restTemplate.setInterceptors(list);
        this.subreddit = subreddit;
        this.minScore = minScore;
    }

    public void collectData() {
        final int limit = 100;
        final int noOfRounds = 80;
        try {
            final FileWriter writer = new FileWriter(TRAINING_FILE);
            for (int i = 0; i < noOfRounds; i++) {
                getPosts(limit, writer);
            }
            writer.close();

            final FileWriter testWriter = new FileWriter(TEST_FILE);
            getPosts(limit, testWriter);
            testWriter.close();
        } catch (final Exception e) {
            logger.error("write to file error", e);
        }
    }

    // ==== private

    private void getPosts(int limit, FileWriter writer) {
        String fullUrl = "http://www.reddit.com/r/" + subreddit + "/new.json?limit=" + limit;
        if (postAfter != null) {
            fullUrl += "&count=" + limit + "&after=" + postAfter;
        }

        try {
            final JsonNode node = restTemplate.getForObject(fullUrl, JsonNode.class);
            parseNode(node, writer);
            Thread.sleep(3000);
        } catch (final Exception e) {
            logger.error("server error", e);
        }

    }

    private void parseNode(JsonNode node, FileWriter writer) throws IOException {
        postAfter = node.get("data").get("after").asText();
        System.out.println(postAfter);
        String line;
        String category;
        List<String> words;
        final SimpleDateFormat df = new SimpleDateFormat("HH");
        for (final JsonNode child : node.get("data").get("children")) {
            category = (child.get("data").get("score").asInt() < minScore) ? "bad" : "good";
            words = Splitter.onPattern("\\W").omitEmptyStrings().splitToList(child.get("data").get("title").asText());
            final Date date = new Date(child.get("data").get("created_utc").asLong() * 1000);

            line = category + ";";
            line += df.format(date) + ";";
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
