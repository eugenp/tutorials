/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.controller.chat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-17 上午11:53
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/chat")
public class ChatController {

    private MsgPublisher msgPublisher = MsgPublisher.getInstance();

    @RequestMapping(params = "command=login")
    public String login(HttpSession session, @RequestParam("username") String username, Model model) {
        session.setAttribute("username", username);
        msgPublisher.login(username);
        Set<String> loginUsers = (Set<String>) msgPublisher.getLoginUsers();
        loginUsers.remove(username);
        model.addAttribute("loginUsers", loginUsers);

        return "chat";
    }

    @RequestMapping(params = "command=poll")
    @ResponseBody
    public DeferredResult<String> poll(HttpServletRequest req) {
        String username = req.getParameter("username");
        if(username == null) {
            username = (String) req.getSession().getAttribute("username");
        }

        if(username != null) {
            return msgPublisher.startAsync(username);
        }
        throw new IllegalStateException("用户名是必须的");
    }


    @RequestMapping(params = "command=send")
    @ResponseBody
    private void send(final HttpServletRequest req) {
        String sender = (String) req.getSession().getAttribute("username");
        String receiver = req.getParameter("receiver");
        String msg = req.getParameter("msg");
        msgPublisher.send(receiver, sender, msg);
    }



}
