package com.sql.database;

import com.sql.security.MySecureBean;

public class DatabaseBean {
	
	private String databasePath;
	private int port;
	private String login;
	private MySecureBean password;
	
	public DatabaseBean(String databasePath, int port, String login, MySecureBean password) {
		this.databasePath = databasePath;
		this.port = port;
		this.login = login;
		this.password = password;
	}
	
	public String getDatabasePath() {
		return databasePath;
	}
	public int getPort() {
		return port;
	}
	public String getLogin() {
		return login;
	}
	public MySecureBean getPassword() {
		return password;
	}
}
