package com.lhj.tutorial;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RepeatVisitor
 * cookie 检测 初访者
 */
@WebServlet("/RepeatVisitor.do")
public class RepeatVisitor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RepeatVisitor() {
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
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean newbie = true;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];
				if ((c.getName().equals("repeatVisitor")) && (c.getValue().equals("yes"))) {
					newbie = false;
					break;
				}
			}
		}
		String title;
		if (newbie) {
			Cookie returnVisitorCookie = new Cookie("repeatVisitor", "yes");
//			returnVisitorCookie.setMaxAge(0);// 清除cookie
			returnVisitorCookie.setMaxAge(365 * 24 * 60 * 60); // 1 year
			response.addCookie(returnVisitorCookie);
			title = "你是初次访问的用户";
		} else {
			title = "你在1年内再次访问该站点";
		}
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.println("<H1 ALIGN=\"center\">" + title + "</H1>");
	}

}
