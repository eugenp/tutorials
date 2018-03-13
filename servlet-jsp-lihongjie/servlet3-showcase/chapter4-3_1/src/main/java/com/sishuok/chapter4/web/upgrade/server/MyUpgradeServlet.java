/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter4.web.upgrade.server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 协议升级：即从现有的http 1.1协议切换到如websocket、http2.0等客户端和服务器都支持的协议上。以达到如websocket全双工通信等。
 *
 * 必须http1.1
 * 具体请参考 HTTP 1.1协议 14.42 Upgrade
 * http://www.cnpaf.net/Class/HTTP/200811/23277.html

 * 升级过程：
 * 1、首先客户端发送请求，头信息中必须带着：
 *  Connection=Upgrade
 *  Upgrade=新的协议
 * 2、如果服务器同意协议，则返回响应：
 * 2.1、响应状态行（Status-Line）：
 *  HTTP/1.1 101 Switching Protocols
 * 2.2、响应头信息：
 *  Connection=Upgrade
 *  Upgrade=新的协议
 *
 * 3、如果服务器不支持协议，可以响应状态行
 *  HTTP/1.1 400 Bad Request
 *
 * 等，具体需要根据协议制定，可以参考websocket 打开阶段握手（ https://github.com/zhangkaitao/websocket-protocol/wiki/4.%E6%89%93%E5%BC%80%E9%98%B6%E6%AE%B5%E6%8F%A1%E6%89%8B）
 * 如上是一个简单的握手。
 *
 * 4、如果切换成功，接着就可以通信了，之后的通信将与Servlet无关。
 *
 * 如下内容摘自Servlet3.1规范（http://jinnianshilongnian.iteye.com/blog/1910981）
 * 1、Servlet过滤器仅处理初始的HTTP请求和响应，然后它们将不会再参与到后续的通信中。换句话说，一旦请求被升级，它们将不会被调用。
 * 2、协议处理器（ProtocolHandler）可以使用非阻塞I/O（non blocking I/O）消费和生产消息。
 * 3、当收到一个升级（upgrade）请求，servlet可以调用HttpServletRequest.upgrade方法启动升级处理。应用准备和发送一个合适的响应到客户端。退出servlet service方法之后，servlet容器完成所有过滤器的处理并标记连接已交给协议处理器处理。然后调用协议处理器的init方法，传入一个WebConnection以允许协议处理器访问数据流。
 * 4、在HTTP/1.1，Upgrade通用头（general-header）允许客户端指定其支持和希望使用的其他通信协议。如果服务器找到合适的切换协议，那么新的协议将在之后的通信中使用。Servlet容器提供了HTTP升级机制。不过，Servlet容器本身不知道任何升级协议。协议处理封装在协议处理器。在容器和协议处理器之间通过字节流进行数据读取或写入。
 *
 *
 * 为了测试，我们制定了两个协议：
 * echo ： 回显
 * time ： 获取当前服务器时间
 *
 *  通过头字段Upgrade指定
 *
 * echo ： 回显
 *   客户端
 *    (内容|BYE)CRLF    ----BYE表示客户端终止连接
 *   服务器
 *    (内容)CRLF
 *
 * time ： 获取当前服务器时间
 *   客户端
 *      (time|BYE)CRLF   ----BYE表示客户端终止连接
 *    服务器
 *      (当前系统时间)CRLF
 *
 * 此处就是一个简单的例子，其实协议升级后就和普通的socket没啥区别了。
 *
 * 关键组件：
 *      HttpUpgradeHandler：协议升级处理器，负责协议升级的，容器将于该处理器通信；
 *        //HTTP Upgrade 升级完成时回调一次（仅一次），接着可以使用新协议来进行客户端/服务器通信（此时容器完全不知道细节）
 *        //使用WebConnection连接进行通信，通过它可以获取输入输出流（可以以非阻塞IO通信）
 *        public void init(WebConnection wc);
 *
 *        当客户端断开连接时回调
 *        public void destroy();
 *
 *
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-20 下午10:59
 * <p>Version: 1.0
 */
@WebServlet(name = "myUpgradeServlet", urlPatterns = "/upgrade")
public class MyUpgradeServlet extends HttpServlet {

    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        String connection = req.getHeader("Connection");
        String upgrade = req.getHeader("Upgrade");
        if(!"Upgrade".equalsIgnoreCase(connection) || upgrade == null) {
            //400 错误请求
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //开始切换协议
        switch (upgrade.toLowerCase()) {
            case "echo" :
                //协议升级处理器。此时可以调用它传些参数
                EchoHttpUpgradeHandler echoHttpUpgradeHandler = req.upgrade(EchoHttpUpgradeHandler.class);
                //告诉切换成功
                resp.setStatus(HttpServletResponse.SC_SWITCHING_PROTOCOLS);
                resp.setHeader("Upgrade", "echo");
                resp.setHeader("Connection", "Upgrade");
                //比如可以通过protocol头 告诉客户端支持的协议列表
                resp.setHeader("protocol", "echo,time");
                break;
            case "time" :
                TimeHttpUpgradeHandler timeHttpUpgradeHandler = req.upgrade(TimeHttpUpgradeHandler.class);
                //告诉切换成功
                resp.setStatus(HttpServletResponse.SC_SWITCHING_PROTOCOLS);
                resp.setHeader("Upgrade", "time");
                resp.setHeader("Connection", "Upgrade");
                //比如可以通过protocol头 告诉客户端支持的协议列表
                resp.setHeader("protocol", "echo,time");
                break;
            default:
                //不支持的协议
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                //比如可以通过protocol头 告诉客户端支持的协议列表
                resp.setHeader("protocol", "echo,time");
                break;
        }

    }
}
