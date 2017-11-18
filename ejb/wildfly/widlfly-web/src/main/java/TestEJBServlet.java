
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import wildfly.beans.UserBeanLocal;

/**
 * Servlet implementation class TestEJBServlet
 */
public class TestEJBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private UserBeanLocal userBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<User> users = userBean.getUsers();

		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head><title>Users</title></head>");
		out.println("<body>");
		out.println("<center><h1>List of users:</h1>");
		out.println("<table border=\"1\" align=\"center\" style=\"width:50%\">");
		for (User user : users) {
			out.println("<tr>");
			out.print("<td>" + user.getUsername() + "</td>");
			out.print("<td>" + user.getEmail() + "</td>");
			out.println("</tr>");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
