package com.lhj.tutorial;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 * 登陆Servlet，主要作用用于设置cookie
 */
@WebServlet(description = "登陆Servlet，设置cookie", urlPatterns = { "/Login.do" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Login() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		//设置Cookie
		Cookie cookie = new Cookie(user, password);
		cookie.setMaxAge(1 * 24 * 60 * 60); //一个星期内有效
		cookie.setPath("/");
		response.addCookie(cookie);
		//设置另外一个Cookie
		Cookie cookie2 = new Cookie("zhangsan", "123");
		response.addCookie(cookie2);
		
		System.out.println("user: " + user + " password: " + password);
	}

}
//http://localhost:8080/SessionManagement/Login.do?user=lihongjie&password=123456
//http://localhost:8080/SessionManagement/IndexServlet.do

