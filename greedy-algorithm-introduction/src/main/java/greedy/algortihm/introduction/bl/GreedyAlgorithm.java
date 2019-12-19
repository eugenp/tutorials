package greedy.algortihm.introduction.bl;

import org.springframework.stereotype.Component;

import greedy.algortihm.introduction.connector.TwitterConnector;
import greedy.algortihm.introduction.model.FollowersPath;
import twitter4j.PagableResponseList;
import twitter4j.TwitterException;
import twitter4j.User;

@Component
public class GreedyAlgorithm {

	int currentLevel = 0;
	final int maxLevel = 3; 
	TwitterConnector tc;
	FollowersPath fp;
	
	public GreedyAlgorithm(TwitterConnector tc) {
		super();
		this.tc = tc;
		this.fp = new FollowersPath();
	}

//	public long findMostFollowersPath(String account) throws TwitterException {		
//		long max = 0;
//		User toFollow = null;
//		
//		PagableResponseList<User> followers = tc.getFollowers(account);
//		for (User el : followers) {
//			long c = el.getFollowersCount();
//			if (c > max) {
//				toFollow = el;
//				max = c;
//			}
//		}
//		
//		if (currentLevel < maxLevel) {
//			currentLevel++;
//			return max + findMostFollowersPath(toFollow.getScreenName());
//		} else {
//			return max;
//		}			
//	}
	
	public void findMostFollowersPath(String account) throws TwitterException {		
		long max = 0;
		User toFollow = null;
		
		PagableResponseList<User> followers = tc.getFollowers(account);
		for (User el : followers) {
			long followersCount = el.getFollowersCount();
			if (followersCount > max) {
				toFollow = el;
				max = followersCount;
			}
		}
		
		if (currentLevel < maxLevel - 1) {
			currentLevel++;
			findMostFollowersPath(toFollow.getScreenName());
			fp.addFollower(toFollow.getScreenName(), max);
			fp.addCount(max);
		} else {
			fp.addFollower(toFollow.getScreenName(), max);
			fp.addCount(max);
		}			
	}
	
	public FollowersPath getFollowers() {
		return fp;
	}
}
