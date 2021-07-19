package com.baeldung.algorithms.greedy;

import java.util.ArrayList;
import java.util.List;

public class FollowersPath {

    private List<Follower> accounts;
    private long count;
    
    public FollowersPath() {
        super();
        this.accounts = new ArrayList<>();
    }
    
    public List<Follower> getAccounts() {
        return accounts;
    }
    public long getCount() {
        return count;
    }
    
    public void addFollower(String username, long count) {
        accounts.add(new Follower(username, count));
    }
    
    public void addCount(long count) {
        this.count += count;
    }
    
    @Override
    public String toString() {
        String details = "";
        for(Follower a : accounts) {
            details+=a.toString() + ", ";
        }
        
        return "Total: " + count + ", \n\r" +
                " Details: { " + "\n\r" + 
                details + "\n\r" + 
                " }";
    }
    
}
