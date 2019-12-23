package com.baeldung.algorithms.greedy;

import lombok.Getter;

public class Follower {

	@Getter String username;
	@Getter long count;
	
	public Follower(String username, long count) {
		super();
		this.username = username;
		this.count = count;
	}
	
	@Override
	public String toString() {
		return "User: " + username + ", Followers: " + count + "\n\r" ; 
	}
}
