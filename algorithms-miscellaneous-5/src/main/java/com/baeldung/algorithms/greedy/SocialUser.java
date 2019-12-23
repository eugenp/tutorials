package com.baeldung.algorithms.greedy;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class SocialUser {

	@Getter private String username;
	@Getter private List<SocialUser> followers;

	public SocialUser(String username) {
		super();
		this.username = username;
		this.followers = new ArrayList<>();
	}
	
	public SocialUser(String username, List<SocialUser> followers) {
		super();
		this.username = username;
		this.followers = followers;
	}
	
	public long getFollowersCount() {
		return followers.size();
	}
	
	public void addFollowers(List<SocialUser> followers) {
		this.followers.addAll(followers);
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((SocialUser) obj).getUsername().equals(username);
	}
	
}
