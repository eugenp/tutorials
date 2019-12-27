package com.baeldung.algorithms.greedy;

import java.util.List;

public class NonGreedyAlgorithm {

    int currentLevel = 0;
    final int maxLevel = 3; 
    SocialConnector tc;
    
    public NonGreedyAlgorithm(SocialConnector tc, int level) {
        super();
        this.tc = tc;
        this.currentLevel = level;
    }
    
    
    public long findMostFollowersPath(String account) throws Exception {        
        List<SocialUser> followers = tc.getFollowers(account);
        long total = currentLevel > 0 ? followers.size() : 0;

        if (currentLevel < maxLevel ) {
            currentLevel++;

            long[] count = new long[followers.size()];
            int i = 0;
            for (SocialUser el : followers) {
                NonGreedyAlgorithm sub = new NonGreedyAlgorithm(tc, currentLevel);
                count[i] = sub.findMostFollowersPath(el.getUsername());
                i++;
            }
            
            long max = 0;
            for (; i > 0; i--) {
                if (count[i-1] > max )
                    max = count[i-1];
            }
            
            return total + max;
        }
        
        return total;
    }
}
