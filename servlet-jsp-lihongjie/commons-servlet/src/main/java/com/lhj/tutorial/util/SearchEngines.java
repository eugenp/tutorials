package com.lhj.tutorial.util;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchEngines
 * 各种搜索引擎的一个前端
 */
@WebServlet("/SearchEngines")
public class SearchEngines extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchEngines() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchString = request.getParameter("searchString");
		if (searchString == null || searchString.length() == 0) {
			reportProblem(response, "Missing search string");
			return;
		}
		searchString = URLEncoder.encode(searchString);
		String searchEngineName = request.getParameter("searchEngine");
		if (searchEngineName == null || searchEngineName.length() == 0) {
			reportProblem(response, "Missing search engine name");
			return;
		}
		
	}

	private void reportProblem(HttpServletResponse response, String string) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
