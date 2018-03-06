package com.lhj.tutorial;

import javax.servlet.http.Cookie;

/**
 * 退出浏览器时cookie自动保持一年，对Cookie进行了扩展.
 * @author lhj
 *
 */
public class LongLivedCookie extends Cookie {
	public static final int SECONDS_PER_YEAR = 365 * 24 * 60 * 60;
	
	public LongLivedCookie(String name, String value) {
		super(name, value);
		setMaxAge(SECONDS_PER_YEAR);
	}
}
