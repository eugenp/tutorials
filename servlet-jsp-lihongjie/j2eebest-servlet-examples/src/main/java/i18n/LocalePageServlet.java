package com.ora.i18n.servlets;

import com.ora.i18n.LocaleSupport;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Locale;

public class LocalePageServlet extends HttpServlet {

    private final static String DEFAULT_PAGE = "index";
    private final static String PAGE_ID_PARAMETER = "pageID";
    private final static String JSP_EXTENSION = ".jsp";

    public LocalePageServlet() {
    }

    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse
            httpServletResponse) throws ServletException, IOException {
        Locale pageLocale;
        pageLocale = (Locale) httpServletRequest.getSession().getAttribute(LocaleSupport.
                USER_PREFERRED_LOCALE);
        if (pageLocale == null) {
            pageLocale = LocaleSupport.APPLICATION_DEFAULT_LOCALE;
        }
        String requestedPage = httpServletRequest.getParameter(PAGE_ID_PARAMETER);
        if (requestedPage == null) {
            requestedPage = DEFAULT_PAGE;
        }
        requestedPage += "_";
        requestedPage += pageLocale.toString();
        requestedPage += JSP_EXTENSION;
        RequestDispatcher rd = httpServletRequest.getRequestDispatcher(requestedPage);
        rd.forward(httpServletRequest, httpServletResponse);
    }

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse
            httpServletResponse) throws ServletException, IOException {
        doPost(httpServletRequest, httpServletResponse);
    }
}
