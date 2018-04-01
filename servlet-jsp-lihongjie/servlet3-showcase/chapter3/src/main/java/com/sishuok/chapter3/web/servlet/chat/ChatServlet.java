/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.servlet.chat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-15 下午8:02
 * <p>Version: 1.0
 */
@WebServlet(name = "chatServlet", urlPatterns = "/chat", asyncSupported = true)
public class ChatServlet extends HttpServlet {

    private MsgPublisher msgPublisher = MsgPublisher.getInstance();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");

        if("login".equals(command)) {
            login(req, resp);
            return;
        } else if("logout".equals(command)) {
            return;
        } else if("send".equals(command)) {
            send(req, resp);
            return;
        } else if("poll".equals(command)) {
            poll(req, resp);
            return;
        }  else {
            //未知命令 忽略
        }

    }

    private void login(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        HttpSession session = req.getSession();
        session.setAttribute("username", username);
        msgPublisher.login(username);
        Set<String> loginUsers = (Set<String>) msgPublisher.getLoginUsers();
        loginUsers.remove(username);
        req.setAttribute("loginUsers", loginUsers);
        req.getRequestDispatcher("/chat.jsp").forward(req, resp);
    }


    private void send(final HttpServletRequest req, final HttpServletResponse resp) {
        String sender = (String) req.getSession().getAttribute("username");
        String receiver = req.getParameter("receiver");
        String msg = req.getParameter("msg");
        msgPublisher.send(receiver, sender, msg);

    }

    private void poll(final HttpServletRequest req, final HttpServletResponse resp) {
        resp.setHeader("Connection", "Keep-Alive");
        resp.addHeader("Cache-Control", "private");
        resp.addHeader("Pragma", "no-cache");
        resp.setContentType("text/html;charset=utf-8");

        String username = req.getParameter("username");
        if(username == null) {
            username = (String) req.getSession().getAttribute("username");
        }

        if(username != null) {
            msgPublisher.startAsync(req, username);
        }

    }



}
