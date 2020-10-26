package com.baeldung.annotation.servletcomponentscan.components;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/hello", initParams = { @WebInitParam(name = "msg", value = "hello") })
public class HelloServlet extends HttpServlet {

    private ServletConfig servletConfig;

    @Override
    public void init(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getOutputStream().write(servletConfig.getInitParameter("msg").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
