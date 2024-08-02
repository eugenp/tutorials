package com.baeldung.setparam;

import java.io.IOException;
import java.util.Locale;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "LanguageServlet", urlPatterns = "/setparam/lang")
public class LanguageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        SetParameterRequestWrapper requestWrapper = new SetParameterRequestWrapper(request);
        requestWrapper.setParameter("locale", Locale.getDefault().getLanguage());
        request.getRequestDispatcher("/setparam/3rd_party_module.jsp").forward(requestWrapper, response);
    }

}