/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter4.web.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-22 下午1:52
 * <p>Version: 1.0
 */
@WebServlet(name = "apiServlet", urlPatterns = "/api")
public class ApiServlet extends HttpServlet {
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        //servlet3.1 新API （目前只有glassfish 4可用）
       ServletContext sc = getServletContext();

        //返回请求内容的长度（long型）
        System.out.println(req.getContentLengthLong());

        //设置long型的内容长度， 而setContentLength()是设置int型的，同理request也提供了类似的方法
//        resp.setContentLengthLong();

        //Virtual Server Name
        //请参考 http://docs.oracle.com/cd/E19879-01/821-0185/ablsw/index.html
        //http://docs.oracle.com/cd/E19776-01/820-4495/ggncs/index.html
        //在glassfish启动时 你可以看到   INFO: Created virtual server server

        //tomcat 和 jetty对应的概念是Virtual host
        //http://www.csse.uwa.edu.au/~ryan/tech/tomcat.html
        //http://docs.codehaus.org/display/JETTY/Virtual+hosts
        System.out.println(sc.getVirtualServerName());


    }
}
