package greedy.algortihm.introduction.connector;

import org.springframework.stereotype.Component;

import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

@Component
public class TwitterConnector {

	Twitter twitter;

	public TwitterConnector() {
		twitter = TwitterFactory.getSingleton();
	}
	
	public PagableResponseList<User> getFollowers(String account) throws TwitterException {
		return twitter.getFollowersList(account, -1L);
	}
	
}
