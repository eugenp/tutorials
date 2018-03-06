/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter4.web.servlet.nonblocking;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 *  写数据客户端，{@link com.sishuok.chapter4.web.servlet.nonblocking.ReadServlet} 负责读
 *
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-20 下午3:52
 * <p>Version: 1.0
 */
@WebServlet(name = "readClientServlet", urlPatterns = "/readClient")
public class ReadClientServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Connection", "Keep-Alive");
        resp.addHeader("Cache-Control", "private");
        resp.addHeader("Pragma", "no-cache");
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write("正在读数据...");
        resp.getWriter().flush();
        resp.flushBuffer();

        StringBuilder url = new StringBuilder("http://");
        url.append(req.getLocalAddr());
        url.append(":");
        url.append(req.getLocalPort());
        url.append(req.getContextPath());
        url.append("/write");

        HttpURLConnection conn = (HttpURLConnection) new URL(url.toString()).openConnection();
        //设置是否从httpUrlConnection输入，默认true
        conn.setDoOutput(true);
        //get方式提交
        conn.setRequestMethod("GET");

        conn.connect();

        InputStream is = new BufferedInputStream(conn.getInputStream());//此处可以使用非阻塞读（目前是阻塞读）

        int count = 0;
        int ch = -1;
        while((ch = is.read()) != -1) {
            count++;
            if(count % (1024 * 100) == 0) {//读1024个字节暂停200毫秒 用于观察非阻塞写
                System.out.println("客户端暂停读取1秒..,已读取:" + count + "个字节");
                try{
                    Thread.sleep(1000L);
                } catch (Exception e) {
                }
            }

            //客户端可强行终止连接
            //此处的目的是为了模拟出客户端主动关闭的情况，即服务器发送了1024*1000，但客户端只读取到1024*500，此时可能导致连接提前中断，服务器写失败
            //需要注释掉后边的is.close();conn.disconnect();
//            if(count >= (1024)) {
//                System.out.println("客户端接收完1024 * 1000个字节");
//                //这将导致服务器端可能还没写完数据，就遭遇了java.io.IOException: 您的主机中的软件放弃了一个已建立的连接
//                //此时服务器端将回调WriteListener#onError
//                is.close();
//                conn.disconnect();
//                break;
//            }
        }

        is.close();
        conn.disconnect();

        resp.getWriter().write("\n客户端读数据完成...");
        resp.getWriter().flush();
        resp.flushBuffer();
    }
}
