/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.servlet.chat;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-15 下午9:03
 * <p>Version: 1.0
 */
public class MsgPublisher {

    private volatile Map<String, Queue<AsyncContext>> usernameToAsyncContextMap = new ConcurrentHashMap();



    private MsgPublisher() {
    }

    private static MsgPublisher instance = new MsgPublisher();
    public static MsgPublisher getInstance() {
        return instance;
    }


    public Collection<String> getLoginUsers() {
        return new HashSet(usernameToAsyncContextMap.keySet());
    }


    public void startAsync(HttpServletRequest req, final String username) {
        //1、开启异步
        final AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(30L * 1000);

        //将异步上下文加入到队列中，这样在未来可以推送消息
        Queue<AsyncContext> queue = usernameToAsyncContextMap.get(username);
        if(queue == null) {
            queue = new ConcurrentLinkedDeque();
            usernameToAsyncContextMap.put(username, queue);
        }

        queue.add(asyncContext);

        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(final AsyncEvent event) throws IOException {
                Queue<AsyncContext> queue = usernameToAsyncContextMap.get(username);
                if(queue != null) {
                    queue.remove(event.getAsyncContext());
                }
            }

            @Override
            public void onTimeout(final AsyncEvent event) throws IOException {
                event.getAsyncContext().complete();
            }

            @Override
            public void onError(final AsyncEvent event) throws IOException {
                Queue<AsyncContext> queue = usernameToAsyncContextMap.get(username);
                if(queue != null) {
                    queue.remove(event.getAsyncContext());
                }
            }

            @Override
            public void onStartAsync(final AsyncEvent event) throws IOException {
            }
        });
    }

    public void login(String username) {
        if (!usernameToAsyncContextMap.containsKey(username)) {
            StringBuilder data = new StringBuilder();
            data.append("{");
            data.append("\"type\" : \"login\"");
            data.append(",\"username\" : \"" + username + "\"");
            data.append("}");
            publish(null, username, data.toString());
        }
    }

    public void logout(String username) {
        if(username == null) {
            return;
        }
        Queue<AsyncContext> queue = usernameToAsyncContextMap.get(username);
        if(queue != null && queue.size() == 0) {
            StringBuilder data = new StringBuilder();
            data.append("{");
            data.append("\"type\" : \"logout\"");
            data.append(",\"username\" : \"" + username + "\"");
            data.append("}");
            publish(null, username, data.toString());
            usernameToAsyncContextMap.remove(username);
        }
    }

    public void send(String receiver, String sender, String msg) {
        StringBuilder data = new StringBuilder();
        data.append("{");
        data.append("\"type\" : \"msg\"");
        data.append(",\"username\" : \"" + sender + "\"");
        data.append(",\"msg\" : \"" + msg + "\"");
        data.append("}");
        publish(receiver, sender, data.toString());
    }

    /**
     *
     * @param receiver 如果为空 表示发送给所有人
     * @param sender
     * @param data
     */
    private void publish(String receiver, String sender, String data) {
        if (receiver == null || receiver.trim().length() == 0) {//发送给所有人
            for (String loginUsername : usernameToAsyncContextMap.keySet()) {
                if (loginUsername.equals(sender)) {
                    continue;
                }
                Queue<AsyncContext> queue = usernameToAsyncContextMap.get(loginUsername);
                if(queue != null) {
                    Iterator<AsyncContext> iter = queue.iterator();
                    while(iter.hasNext()) {
                        AsyncContext asyncContext = iter.next();
                        try {
                            ServletResponse response = asyncContext.getResponse();
                            PrintWriter out = response.getWriter();
                            out.write(data);
                            out.flush();
                            asyncContext.complete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else { //私人消息
            Queue<AsyncContext> queue = usernameToAsyncContextMap.get(receiver);
            if(queue != null) {
                Iterator<AsyncContext> iter = queue.iterator();
                while(iter.hasNext()) {
                    AsyncContext asyncContext = iter.next();
                    try {
                        ServletResponse response = asyncContext.getResponse();
                        PrintWriter out = response.getWriter();
                        out.write(data);
                        out.flush();
                        asyncContext.complete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
