/**
 * 
 */
package com.baeldung;

import java.util.List;
import java.util.stream.Collectors;

import twitter4j.DirectMessage;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Application {

	public static Twitter getTwitterinstance() {
		/**
		 * if not using properties file, we can set access token by following way
		 */
//		ConfigurationBuilder cb = new ConfigurationBuilder();
//		cb.setDebugEnabled(true)
//		  .setOAuthConsumerKey("//TODO")
//		  .setOAuthConsumerSecret("//TODO")
//		  .setOAuthAccessToken("//TODO")
//		  .setOAuthAccessTokenSecret("//TODO");
//		TwitterFactory tf = new TwitterFactory(cb.build());
//		Twitter twitter = tf.getSingleton();
		
		Twitter twitter = TwitterFactory.getSingleton();
		return twitter;
		
	}
	
	public static String createTweet(String tweet) throws TwitterException {
		Twitter twitter = getTwitterinstance();
		Status status = twitter.updateStatus("creating baeldung API");
	        return status.getText();
	}
	
	public static List<String> getTimeLine() throws TwitterException {
		Twitter twitter = getTwitterinstance();
		List<Status> statuses = twitter.getHomeTimeline();
		return statuses.stream().map(
				item -> item.getText()).collect(
						Collectors.toList());
	}
	
	public static String sendDirectMessage(String recipientName, String msg) throws TwitterException {
		Twitter twitter = getTwitterinstance();
	        DirectMessage message = twitter.sendDirectMessage(recipientName, msg);
	        return message.getText();
	}
	
	public static List<String> searchtweets() throws TwitterException {
		Twitter twitter = getTwitterinstance();
	        Query query = new Query("source:twitter4j baeldung");
	        QueryResult result = twitter.search(query);
	        List<Status> statuses = result.getTweets();
	        return statuses.stream().map(
				item -> item.getText()).collect(
						Collectors.toList());
	}
	
	public static void streamFeed() {
		
		StatusListener listener = new StatusListener(){

			@Override
			public void onException(Exception e) {
				e.printStackTrace();
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg) {
                                System.out.println("Got a status deletion notice id:" + arg.getStatusId());
			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
                                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			}

			@Override
			public void onStallWarning(StallWarning warning) {
                                System.out.println("Got stall warning:" + warning);
			}

			@Override
			public void onStatus(Status status) {
                                System.out.println(status.getUser().getName() + " : " + status.getText());
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			}
		};
	
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		
	        twitterStream.addListener(listener);
	    
	        twitterStream.sample();
		
	}
	
}
