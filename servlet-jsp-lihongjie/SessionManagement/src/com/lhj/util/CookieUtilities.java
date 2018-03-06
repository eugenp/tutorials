package com.lhj.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description Some static methods for use in cookie handling. 
 * @author lhj
 * @date 2015-5-4 14:32:39
 * @version 1.0
 */
public class CookieUtilities {
	/**
	 * Cookie的名字
	 */
	public static final String COOKIE_HISTORYID = "cookies_history";
	
	public static final String USER_ID = "dangdang.user.id";
	
	public static final String USER_TEMP_ID = "dangdang.user.temp.id";
	
	public static final String CART_ID = "dangdang.cart.id";
	/**
	 * cookie的存活时间
	 */
	public static final int COOKIE_ONE_WEEK_LIFE_TIME = 7 * 24 * 60 * 60; // 一周
	/**
	 * 最大的浏览历史记录条数
	 */
	public static final int HISTORY_COUNT = 5;
	
	/**
	 * 给定名称的cookie值的获取，
	 * @param request HttpServletRequest
	 * @param cookieName 名称
	 * @param defaultValue 默认值
	 * @return 返回名称与它输入相匹配的任何Cookie的值(名称为cookieName的value值)，如果不存在这类匹配，则返回指定的默认值defaultValue.
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName, String defaultValue) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookieName.equals(cookie.getName())) {
					return (cookie.getValue());
				}
			}
		}
		return defaultValue;
	}
	
	/**
	 * 给定名称的cookie的获取，注意：必须重新指定cookie所有的属性，比如日期、路径和域等.
	 * @param request
	 * @param cookieName
	 * @return 返回指定名称的cookie，没有返回null.
	 */
	public static Cookie getCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookieName.equals(cookie.getName())) {
					return cookie;
				}
			}
		}
		return null;
	}
	
}
