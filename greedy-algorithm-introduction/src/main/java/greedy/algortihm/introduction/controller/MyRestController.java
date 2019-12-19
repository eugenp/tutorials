package greedy.algortihm.introduction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import greedy.algortihm.introduction.bl.GreedyAlgorithm;
import greedy.algortihm.introduction.bl.NonGreedyAlgorithm;
import greedy.algortihm.introduction.connector.TwitterConnector;
import twitter4j.TwitterException;

@RestController
public class MyRestController {

	@Autowired 
	TwitterConnector tc;
	
	@GetMapping(path = "/greedy/followers") 
	public String getFollowersGreedy(@RequestParam String account) throws TwitterException {
		GreedyAlgorithm ga = new GreedyAlgorithm(tc);
		ga.findMostFollowersPath(account);
		return "Reachable accounts: #" + ga.getFollowers(); 
			//ga.findMostFollowersPath(account);
	}
	
	@GetMapping(path = "/nongreedy/followers") 
	public String getFollowersNonGreedy(@RequestParam String account) throws TwitterException {
		NonGreedyAlgorithm nga = new NonGreedyAlgorithm(tc);
		return "Reachable accounts: #" + nga.findMostFollowersPath(account);
	}
}
