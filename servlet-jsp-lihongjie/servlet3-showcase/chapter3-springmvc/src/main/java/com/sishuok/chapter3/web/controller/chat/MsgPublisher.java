/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter3.web.controller.chat;

import org.springframework.web.context.request.async.DeferredResult;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-15 下午9:03
 * <p>Version: 1.0
 */
public class MsgPublisher {

    private volatile Map<String, Queue<DeferredResult<String>>> usernameToDeferredResultMap = new ConcurrentHashMap();



    private MsgPublisher() {
    }

    private static MsgPublisher instance = new MsgPublisher();
    public static MsgPublisher getInstance() {
        return instance;
    }


    public Collection<String> getLoginUsers() {
        return new HashSet(usernameToDeferredResultMap.keySet());
    }


    public DeferredResult<String> startAsync(final String username) {
        final DeferredResult<String> result = new DeferredResult<String>(30L * 1000, null);

        final Runnable removeDeferredResultRunnable = new Runnable() {
            @Override
            public void run() {
                Queue<DeferredResult<String>> queue = usernameToDeferredResultMap.get(username);
                if(queue != null) {
                    queue.remove(result);
                }
            }
        };
        result.onCompletion(removeDeferredResultRunnable);
        result.onTimeout(removeDeferredResultRunnable);

        //将异步上下文加入到队列中，这样在未来可以推送消息
        Queue<DeferredResult<String>> queue = usernameToDeferredResultMap.get(username);
        if(queue == null) {
            queue = new ConcurrentLinkedDeque();
            usernameToDeferredResultMap.put(username, queue);
        }
        queue.add(result);

        return result;
    }

    public void login(String username) {
        if (!usernameToDeferredResultMap.containsKey(username)) {
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
        Queue<DeferredResult<String>> queue = usernameToDeferredResultMap.get(username);
        boolean isLogout = false;
        if (queue != null) {
            if (queue.size() == 0) {
                isLogout = true;
            } else {
                isLogout = true;
                Iterator<DeferredResult<String>> iter = queue.iterator();
                while (iter.hasNext()) {
                    DeferredResult<String> result = iter.next();
                    if (!result.isSetOrExpired() ) {
                        isLogout = false;
                        break;
                    }
                }
            }
        }

        if(isLogout) {
            StringBuilder data = new StringBuilder();
            data.append("{");
            data.append("\"type\" : \"logout\"");
            data.append(",\"username\" : \"" + username + "\"");
            data.append("}");
            publish(null, username, data.toString());
            usernameToDeferredResultMap.remove(username);
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
            for (String loginUsername : usernameToDeferredResultMap.keySet()) {
                if (loginUsername.equals(sender)) {
                    continue;
                }
                Queue<DeferredResult<String>> queue = usernameToDeferredResultMap.get(loginUsername);
                if(queue != null) {
                    Iterator<DeferredResult<String>> iter = queue.iterator();
                    while(iter.hasNext()) {
                        DeferredResult<String> result = iter.next();
                        try {
                            result.setResult(data);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        iter.remove();
                    }
                }
            }
        } else { //私人消息
            Queue<DeferredResult<String>> queue = usernameToDeferredResultMap.get(receiver);
            if(queue != null) {
                Iterator<DeferredResult<String>> iter = queue.iterator();
                while(iter.hasNext()) {
                    DeferredResult result = iter.next();
                    try {
                        result.setResult(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    iter.remove();
                }
            }
        }
    }

}
