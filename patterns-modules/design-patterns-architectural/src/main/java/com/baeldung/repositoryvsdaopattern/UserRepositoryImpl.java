package com.baeldung.repositoryvsdaopattern;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private UserDaoImpl userDaoImpl;
    private TweetDaoImpl tweetDaoImpl;
    
    @Override
    public User get(Long id) {
        UserSocialMedia user = (UserSocialMedia) userDaoImpl.read(id);
        
        List<Tweet> tweets = tweetDaoImpl.fetchTweets(user.getEmail());
        user.setTweets(tweets);
        
        return user;
    }
    
    @Override
    public void add(User user) {
        userDaoImpl.create(user);
    }

    @Override
    public void remove(User user) {
        
    }

    @Override
    public void update(User user) {
        
    }

    @Override
    public List<Tweet> fetchTweets(User user) {
        return tweetDaoImpl.fetchTweets(user.getEmail());
        
    }
    
    @Override
    public User findByUserName(String userName) {
        return null;
    }
    
    @Override
    public User findByEmail(String email) {
        return null;
    }

}
