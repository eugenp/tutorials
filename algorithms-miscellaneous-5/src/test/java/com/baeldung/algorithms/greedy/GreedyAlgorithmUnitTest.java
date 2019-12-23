package com.baeldung.algorithms.greedy;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GreedyAlgorithmUnitTest {
	
	private SocialConnector prepareNetwork() {
		SocialConnector sc = new SocialConnector();

		SocialUser root = new SocialUser("root");
		SocialUser child1 = new SocialUser("child1");
		SocialUser child2 = new SocialUser("child2");
		SocialUser child3 = new SocialUser("child3");
		SocialUser child21 = new SocialUser("child21");
		SocialUser child211 = new SocialUser("child211");
		SocialUser child2111 = new SocialUser("child2111");
		SocialUser child31 = new SocialUser("child31");
		SocialUser child311 = new SocialUser("child311");
		SocialUser child3111 = new SocialUser("child3111");

		
		child211.addFollowers(Arrays.asList(new SocialUser[]{child2111}));
		child311.addFollowers(Arrays.asList(new SocialUser[]{child3111}));

		child21.addFollowers(Arrays.asList(new SocialUser[]{child211}));
		child31.addFollowers(Arrays.asList(new SocialUser[]{child311, 
				new SocialUser("child312"), new SocialUser("child313"), new SocialUser("child314")}));

		child1.addFollowers(Arrays.asList(new SocialUser[]{new SocialUser("child11"), new SocialUser("child12")}));
		child2.addFollowers(Arrays.asList(new SocialUser[]{child21, new SocialUser("child22"), new SocialUser("child23")}));
		child3.addFollowers(Arrays.asList(new SocialUser[]{child31}));

		root.addFollowers(Arrays.asList(new SocialUser[]{child1, child2, child3}));

		sc.setUsers(Arrays.asList(new SocialUser[]{root, child1, child2, child3, child21, child31, child311, child211}));
		return sc;
	}
	
	@Test
	public void greedyAlgorithmTest() throws Exception {
		GreedyAlgorithm ga = new GreedyAlgorithm(prepareNetwork());
		assertEquals(ga.findMostFollowersPath("root"), 5);
	}
	
	@Test
	public void nongreedyAlgorithmTest() throws Exception {
		NonGreedyAlgorithm nga = new NonGreedyAlgorithm(prepareNetwork(), 0);
		Assertions.assertThrows(Exception.class, () -> {
			nga.findMostFollowersPath("root");
		  });
	}
	
	@Test
	public void nongreedyAlgorithmUnboundedTest() throws Exception {
		SocialConnector sc = prepareNetwork();
		sc.switchCounter();
		NonGreedyAlgorithm nga = new NonGreedyAlgorithm(sc, 0);
		assertEquals(nga.findMostFollowersPath("root"), 6);
	}
	
}