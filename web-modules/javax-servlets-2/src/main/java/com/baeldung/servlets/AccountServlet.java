package com.baeldung.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.servlets.clientinfo.AccountLogic;

@WebServlet(name = "AccountServlet", urlPatterns = "/account")
public class AccountServlet extends HttpServlet {
    public static final Logger log = LoggerFactory.getLogger(AccountServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AccountLogic accountLogic = new AccountLogic();
        Map<String, String> clientInfo = accountLogic.getClientInfo(request);
        log.info("Request client info: {}, " + clientInfo);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
