/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter4.web.upgrade.server;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.WebConnection;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-20 下午10:59
 * <p>Version: 1.0
 */
public class TimeHttpUpgradeHandler implements HttpUpgradeHandler {

    private ServletInputStream is;
    private ServletOutputStream os;

    /**
     *  HTTP Upgrade 升级完成时回调一次（仅一次），接着可以使用新协议来进行客户端/服务器通信（此时容器完全不知道细节）
     *  使用WebConnection连接进行通信，通过它可以获取输入输出流（可以以非阻塞I/O通信）
     *
     * @param wc
     */
    @Override
    public void init(final WebConnection wc) {
        try {
            is = wc.getInputStream();
            os = wc.getOutputStream();
            //采用非阻塞I/O读写
            is.setReadListener(new EchoReadListener());
            //刷出下（如写出的响应头）
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 当客户端断开连接时回调
     */
    @Override
    public void destroy() {
        System.out.println("===连接终止");
    }

    private class EchoReadListener implements ReadListener {
        private StringBuilder buffer = new StringBuilder();
        @Override
        public void onDataAvailable() throws IOException {
            int ch = -1;
            while(is.isReady() && ((ch = is.read()) != -1)) {
                buffer.append((char)ch);

                int bufferLength = buffer.length();
                //如果接收到的是CRLF，表示一次读取完毕
                if(bufferLength >= 2 &&
                        buffer.charAt(bufferLength - 2) == '\r' && buffer.charAt(bufferLength - 1) == '\n') {
                    String data = buffer.toString();
                    if("BYE\r\n".equals(data)) {//结束通信
                        System.out.println("服务器结束通信:" + data);
                        os.close();
                        is.close();
                        break;
                    } else if("time\r\n".equalsIgnoreCase(data)) {
                        System.out.println("服务器接收到数据:" + data);
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        os.write((df.format(new Date()) + "\r\n").getBytes("ISO-8859-1"));
                        os.flush();
                    } else {
                        os.write("error command, only support 'time' command\r\n".getBytes("ISO-8859-1"));
                        os.flush();
                    }
                    buffer = new StringBuilder();
                }
            }
        }

        @Override
        public void onAllDataRead() throws IOException {

        }

        @Override
        public void onError(final Throwable t) {
            System.out.println("服务器端读出错了");
            t.printStackTrace(System.out);
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
