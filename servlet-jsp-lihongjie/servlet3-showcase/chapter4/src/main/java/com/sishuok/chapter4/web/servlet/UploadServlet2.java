/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter4.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-22 下午3:04
 * <p>Version: 1.0
 */
@MultipartConfig(
        location = "D:\\Backup", //即默认为 javax.servlet.context.tempdir 如mvn jetty:run 在chapter4\target\tmp中
        maxRequestSize = 1024 * 1024 * 2,   //指定一次请求最大的上传数据量（上传的总和） 单位：字节， -1表示不限制
        maxFileSize = 1024 * 1024 * 1, //设定单个文件的最大大小，-1表示不限制
        fileSizeThreshold = 100000 //当上传的文件大小大于该值时才写入文件
)
@WebServlet(name = "uploadServlet2", urlPatterns = "/upload2")
public class UploadServlet2 extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("file1");
        //写到相对于 MultipartConfig中的location位置
        part.write("a.txt");
        //不保证多次调用成功，使用jetty测试时发现当调用多次，最后一次有效，即上传的文件会重命名为最后一次的文件名
        part.write("b.txt");

        //调用完InputStream记得关闭，否则删除不掉
        InputStream is = part.getInputStream();
        is.close(); //不能 part.getInputStream().close();//相当于多次获取
        //删除相关的文件
        part.delete();
    }

}
