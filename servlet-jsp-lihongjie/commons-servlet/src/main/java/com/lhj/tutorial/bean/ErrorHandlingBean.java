package com.lhj.tutorial.bean;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErrorHandlingBean {
	public static String CLOSE_WINDOW = "javascript:window.close();";
	public static String PREV_WINDOW = "javascript:history.back();";
	public static String UNKNOW_ERROR = "发现未知错误，请与管理员联系！";
	private static String UNKNOW_ERROR_en = "Found Unknow Error,Please Contact the Web Master!";
	public static String[] ErrorTypes = {"系统错误","数据错误","DB2数据库错误","用户定义错误"};
	private static String[] ErrorTypes_en = {"System Error", "Data Error", "Dadabase Error", "User Define Error"};
	public static int SYSTEM_ERR = 0;
	public static int DATA_ERR = 1;
	public static int DB_ERR = 2;
	public static int USER_ERR = 3;
	public Exception Err_ExceptMsg = null;
	public String Err_Src;
	public String Err_ID;
	public String Err_nextStep;
	private HttpSession session;
	private HttpServletResponse response;
	private String LANG;
	
	public ErrorHandlingBean(HttpSession ses, HttpServletResponse res) {
		super();
		this.session = ses;
		this.response = res;
		setLanguage();
	}


	
	/**
	 * 清除会话中与错误管理器相关的变量
	 */
	public void cleanErrorSession() {
		session.removeAttribute("Err_Src");
		session.removeAttribute("Err_ExceptMsg");
		session.removeAttribute("Err_ID");
		session.removeAttribute("Err_nextStep");
	}
	
	public void doProcessError() {
		/*国际化*/
		if (Err_Src == null) {
			if (LANG != null && LANG.compareTo("english") == 0)
				Err_Src = ErrorTypes_en[DATA_ERR];
			else
				Err_Src = ErrorTypes[DATA_ERR];
		}
		if (Err_nextStep == null)
			Err_nextStep = CLOSE_WINDOW;
		if (Err_ExceptMsg == null || Err_ExceptMsg.getMessage() == null) {
			if (LANG != null && LANG.compareTo("english") == 0)
				Err_ExceptMsg = new Exception(UNKNOW_ERROR_en);
			else
				Err_ExceptMsg = new Exception(UNKNOW_ERROR);
		}
	}
	
	public void doRead() {
		getOrigError();
		doProcessError();
		isDB2Err();
		cleanErrorSession();
	}
	
	public void doWrite() {
		session.setAttribute("Err_Src", Err_Src);
		session.setAttribute("Err_ID", Err_ID);
		session.setAttribute("Err_ExceptMsg", Err_ExceptMsg);
		session.setAttribute("Err_nextStep", Err_nextStep);
	}
	
	/**
	 * 此方法主要用于收集错误的代码类型和错误信息，然后直接交给错误中心处理
	 * @param err_src
	 * @param err_nextstep
	 * @param err_Exception
	 */
	public void gatherErrotoCenter(int err_src, String err_nextstep, String err_Exception) throws Exception {
		if (LANG != null && LANG.compareTo("english") == 0)
			Err_Src = ErrorTypes_en[err_src];
		else
			Err_Src = ErrorTypes[err_src];
		Err_nextStep = err_nextstep;
		Err_ExceptMsg = new Exception(err_Exception);
		doWrite();            //将错误信息写入会话中
		toErrorCenter();
	}
	
	/**
	 * 此方法主要用于收集错误的代码类型和错误信息，然后直接交给错误中心处理
	 * @param err_src
	 * @param err_nextstep
	 * @param err_Except
	 * @throws Exception
	 */
	public void gatherErrotoCenter(String err_src, String err_nextstep, Exception err_Except) throws Exception {
		Err_Src = err_src;
		Err_nextStep = err_nextstep;
		Err_ExceptMsg = err_Except;
		doWrite();            //将错误信息写入会话中
		toErrorCenter();
	}

	/**
	 * 根据会话中的国际化信息选择对应的错误信息字符集，重定向到错误处理页面
	 * @throws Exception
	 */
	private void toErrorCenter() throws Exception {
		if (LANG != null && LANG.compareTo("english") == 0)
			response.sendRedirect(response.encodeURL("/xxx/ErrorPages.jsp"));
		else
			response.sendRedirect(response.encodeURL("/HallConf/ErrorPages.jsp"));
	}

	private void getOrigError() {
		Err_Src = (String) session.getAttribute("Err_Src");
		Err_ID = (String) session.getAttribute("Err_ID");
		Err_ExceptMsg = (Exception) session.getAttribute("Err_ExceptMsg");
		Err_nextStep = (String) session.getAttribute("Err_nextStep");
	}
	
	public boolean isDataErr() {
		return true;
	}
	
	public boolean isDB2Err() {
		if (Err_ExceptMsg.getMessage().indexOf("DB2") > 0) {
			if (LANG != null && LANG.compareTo("english") == 0) 
				Err_Src = ErrorTypes_en[DB_ERR];
			else
				Err_Src = ErrorTypes[DB_ERR];
			return true;
		}
		return false;
	}

	public Exception getErr_ExceptMsg() {
		return Err_ExceptMsg;
	}

	
	public void setErr_ExceptMsg(Exception err_ExceptMsg) {
		Err_ExceptMsg = err_ExceptMsg;
	}

	public String getErr_ID() {
		return Err_ID;
	}
	
	public void setErr_ID(String err_ID) {
		Err_ID = err_ID;
	}
	
	public String getErr_nextStep() {
		return Err_nextStep;
	}

	public void setErr_nextStep(String err_nextStep) {
		Err_nextStep = err_nextStep;
	}

	public String getErr_Src() {
		return Err_Src;
	}

	public void setErr_Src(String err_Src) {
		Err_Src = err_Src;
	}
	
	public void setLanguage() {
		LANG = (String) session.getAttribute("LANG");
	}
}
