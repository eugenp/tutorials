package com.baeldung.boot.configurationproperties;

public class Credentials {

	private String username;
	private String password;
	
	public Credentials() {System.out.println("### INIT2 ###");}

	public Credentials(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
