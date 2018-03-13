package com.concretepage;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String attributeName = req.getParameter("attributeName");
		String attributeValue = req.getParameter("attributeValue");
        //add attribute in HttpSession
		req.getSession().setAttribute(attributeName, attributeValue);
		resp.sendRedirect(req.getContextPath() + "/");
	}
}
