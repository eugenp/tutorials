package com.lhj.tutorial.util;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lhj.tutorial.bean.ErrorHandlingBean;

/**
 * Servlet implementation class I18NServlet
 */
@WebServlet("/I18NServlet")
public class I18NServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private Hashtable languageNames = new Hashtable();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public I18NServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		performTask(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		performTask(request, response);
	}

	
	@Override
	public String getServletInfo() {
		return super.getServletInfo();
	}

	@Override
	public void init() throws ServletException {
		languageNames.put("en", "english");
		languageNames.put("zh", "chinese");
		languageNames.put("zh-cn", "chinese/china");
	}

	private void performTask(HttpServletRequest request,
			HttpServletResponse response) {
		ErrorHandlingBean errHandler = new ErrorHandlingBean(request.getSession(), response);
		try {
			String Actions = request.getParameter("sub");
			if (Actions == null)
				errHandler.gatherErrotoCenter(errHandler.SYSTEM_ERR, errHandler.CLOSE_WINDOW, (String)null);
			String Language = request.getParameter("LANG");
			if (Language == null)
				errHandler.gatherErrotoCenter(errHandler.SYSTEM_ERR, errHandler.CLOSE_WINDOW, (String)null);
			String Lang_pre = "";
			String LangSet = (String) languageNames.get(Language);
			if (LangSet.compareTo("english") == 0)
				Lang_pre = "/xx_en";
			if (LangSet.compareTo("chinese/china") == 0)
				Lang_pre = "";
			if (Actions.compareTo("Register") == 0) {
				request.getSession().setAttribute("LANG", LangSet);
				getServletContext().getRequestDispatcher(Lang_pre + "/login.jsp").forward(request, response);
			} else {
				request.getSession().setAttribute("LANG", LangSet);
				getServletContext().getRequestDispatcher(Lang_pre + "/login.jsp").forward(request, response);

			}
		}
		catch (Exception e) {
			
		}
		
	}
}
