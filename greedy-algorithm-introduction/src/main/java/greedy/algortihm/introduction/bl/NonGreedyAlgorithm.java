package greedy.algortihm.introduction.bl;

import java.util.List;

import org.springframework.stereotype.Component;

import greedy.algortihm.introduction.connector.TwitterConnector;
import twitter4j.PagableResponseList;
import twitter4j.TwitterException;
import twitter4j.User;

@Component
public class NonGreedyAlgorithm {

	int currentLevel = 0;
	final int maxLevel = 3; 
	TwitterConnector tc;
	
	public NonGreedyAlgorithm(TwitterConnector tc) {
		super();
		this.tc = tc;
	}
	
	public long findMostFollowersPath(String account) throws TwitterException {		
		
		PagableResponseList<User> followers = tc.getFollowers(account);
		long total = currentLevel > 0 ? followers.size() : 0;
		
		if (currentLevel < maxLevel - 1) {
			List<User> sl = followers.subList(0, 3);
			currentLevel++;

			long[] count = new long[sl.size()];
			int i = 0;
			for (User el : sl) {
				count[i] = findMostFollowersPath(el.getScreenName());
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
