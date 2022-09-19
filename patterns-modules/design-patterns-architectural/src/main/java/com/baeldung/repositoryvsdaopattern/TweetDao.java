package com.baeldung.repositoryvsdaopattern;

import java.util.List;

public interface TweetDao {

    List<Tweet> fetchTweets(String email);
    
}
