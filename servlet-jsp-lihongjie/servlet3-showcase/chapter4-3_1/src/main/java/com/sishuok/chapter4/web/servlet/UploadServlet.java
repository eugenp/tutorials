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

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-6-22 下午3:04
 * <p>Version: 1.0
 */
@MultipartConfig(
        location = "", //即默认为 javax.servlet.context.tempdir 如mvn jetty:run 在chapter4\target\tmp中
        maxRequestSize = 1024 * 1024 * 2,   //指定一次请求最大的上传数据量（上传的总和） 单位：字节， -1表示不限制
        maxFileSize = 1024 * 1024 * 1, //设定单个文件的最大大小，-1表示不限制
        fileSizeThreshold = 1024 * 1024 * 10 //当上传的文件大小大于该值时才写入文件
)
@WebServlet(name = "uploadServlet", urlPatterns = "/upload")
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        //访问 /upload.jsp

        System.out.println(req.getParameter("name"));

        Part part = req.getPart("file1");
        //servlet 3.1 可以直接调用getSubmittedFileName得到客户端提交时的文件名
        System.out.println(part.getSubmittedFileName());
    }


}
