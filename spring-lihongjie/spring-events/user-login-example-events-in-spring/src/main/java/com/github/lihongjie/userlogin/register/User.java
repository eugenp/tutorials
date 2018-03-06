package com.github.lihongjie.userlogin.register;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = -3146646427997695354L;
	
	private String username;
    private String password;

    //为了测试方便 简化了实现
    public User(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
