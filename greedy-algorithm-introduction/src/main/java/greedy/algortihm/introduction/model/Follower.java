package greedy.algortihm.introduction.model;

public class Follower {

	private String username;
	private long count;
	
	public Follower(String username, long count) {
		super();
		this.username = username;
		this.count = count;
	}

	public String getUsername() {
		return username;
	}

	public long getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "User: " + username + ", Followers: " + count + "\n\r" ; 
	}
	
}
