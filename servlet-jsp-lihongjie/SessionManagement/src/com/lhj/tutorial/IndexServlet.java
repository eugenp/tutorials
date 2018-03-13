package com.lhj.tutorial;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class IndexServlet
 * 获取Login中设置的Cookie,并输出.
 * 问题： cookies[i].getMaxAge() 最大限制周期一直输出位 -1 ？
 *     cookies[i].getPath() 为null.
 */
@WebServlet("/IndexServlet.do")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
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
	 * @description cookie与主机（或域）相关，这种方法获取的cookie，直接循环遍历出来有问题。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				String name = cookies[i].getName();
				String value = cookies[i].getValue();
				System.out.println("name : " + name + " value: " + value);
				System.out.println("cookies的getComment : " + cookies[i].getComment());
				System.out.println("cookies的getDomain : " + cookies[i].getDomain());
				System.out.println("cookies的getMaxAge : " + cookies[i].getMaxAge());
				System.out.println("cookies的getPath : " + cookies[i].getPath());
				System.out.println("cookies的getVersion : " + cookies[i].getVersion());
				System.out.println("cookies的getSecure : " + cookies[i].getSecure());
				cookies[i].setMaxAge(24*60*60);
				response.addCookie(cookies[i]);
			}
		} else {
			System.out.println("cookie 为 null");
		}
	}

}
