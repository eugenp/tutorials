package com.baeldung.dispatchservlet.servlet;

import com.baeldung.dispatchservlet.filter.CustomFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(CustomServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("CustomServlet doGet() method is invoked");
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("CustomServlet doPost() method is invoked");
        super.doPost(req, resp);
    }
}
