package com.baeldung.annotation.servletcomponentscan.components;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
