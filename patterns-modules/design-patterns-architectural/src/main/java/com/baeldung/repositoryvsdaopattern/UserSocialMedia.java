package com.baeldung.repositoryvsdaopattern;

import java.util.List;

public class UserSocialMedia extends User {
    
    private List<Tweet> tweets;
    
    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

}
