package com.lhj.tutorial;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhj.util.CookieUtilities;

/**
 * Servlet implementation class ClientAccessCounts
 * 记录用户的访问计数.跟踪每个客户对特定页面的访问次数。通过生成名为accessCount的cookie(它的值为实际的访问计数),
 * 在这个过程中，servlet需要用相同的名称再次发送cookie，不断地替换该cookie的值。
 * 注意：每个用户都会看到他们自己的访问计数，同样， IE 和 火狐独立的维护cookie，因此同一个用户在这两种浏览器中看到的独立的访问计数。
 */
@WebServlet("/ClientAccessCounts.do")
public class ClientAccessCounts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientAccessCounts() {
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
		String countString = CookieUtilities.getCookieValue(request, "accessCount", "1");
		int count = 1;
		try {
			count = Integer.parseInt(countString);
		} catch (NumberFormatException e) {
			
		}
		LongLivedCookie c = new LongLivedCookie("accessCount", String.valueOf(count + 1));
		response.addCookie(c);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("This is visit number " + count + " by this browser.");
	}

}
