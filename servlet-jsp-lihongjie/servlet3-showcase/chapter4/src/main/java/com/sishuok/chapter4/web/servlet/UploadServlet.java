/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.chapter4.web.servlet;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

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
        location = "", //即默认为 javax.servlet.context.tempdir 如mvn jetty:run 在chapter4\target\tmp中
        maxRequestSize = 1024 * 1024 * 2,   //指定一次请求最大的上传数据量（上传的总和） 单位：字节， -1表示不限制
        maxFileSize = 1024 * 1024 * 1, //设定单个文件的最大大小，-1表示不限制
        fileSizeThreshold = 1024 * 1024 * 10 //当上传的文件大小大于该值时才写入文件
)
@WebServlet(name = "uploadServlet1", urlPatterns = "/upload")
public class UploadServlet extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        try {

            //如果servlet容器还没有处理Part部分，那么返回null
            //9.0.4.v20130625 已经修复了这个问题
            System.out.println(req.getParameter("name"));

            //获取Part部分
            System.out.println("\n\n==========file1");
            Part file1Part = req.getPart("file1");  //只要调用 就会触发所有Part的上传
            InputStream file1PartInputStream = file1Part.getInputStream();
            System.out.println(IOUtils.toString(file1PartInputStream));
            file1PartInputStream.close();

            //如果多个 只获取第一个
            System.out.println("\n\n==========file2");
            Part file2Part = req.getPart("file2");
            InputStream file2PartInputStream = file2Part.getInputStream();
            System.out.println(IOUtils.toString(file2PartInputStream));
            file2PartInputStream.close();

            System.out.println("\n\n==========parameter name");
            //此时可以通过如下的一种得到表单数据
            System.out.println(IOUtils.toString(req.getPart("name").getInputStream()));
            //或当Part已经解析后 jetty会自动添加到parameters中，可以直接获取
            System.out.println(req.getParameter("name"));

            //如果容器还是不能处理 只能 req.getInputStream(); 然后自己解析

            System.out.println("\n\n=============all part");
            for(Part part : req.getParts()) {
                System.out.println("\n\n=========name:::" + part.getName());
                System.out.println("=========size:::" + part.getSize());
                System.out.println("=========content-type:::" + part.getContentType());
                System.out.println("=========header content-disposition:::" + part.getHeader("content-disposition"));
                System.out.println("=========file name:::" + getFileName(part));
                InputStream partInputStream = part.getInputStream();
                System.out.println("=========value:::" + IOUtils.toString(partInputStream));

                //别忘了关闭
                partInputStream.close();
                //删除 如果打开流 不关闭 将删除不了
                part.delete();

            }


        } catch (IllegalStateException ise) {
            //文件上传失败
            ise.printStackTrace();
            String errorMsg = ise.getMessage();
            if(errorMsg.contains("Request exceeds maxRequestSize")) {
                //所有上传的部分超出了整个上传大小
            } else if(errorMsg.contains("Multipart Mime part file1 exceeds max filesize")) {
                //某个文件 超出单个文件上传大小
            } else {
                //其他错误
            }
        }

    }

    //没有提供直接获取文件名的API，需要拿到content-disposition再解析
    //servlet 3.1 直接使用Part.getSubmittedFileName即可拿到客户端的文件名
    private String getFileName(final Part part) {
        if(part == null) {
            return null;
        }

        if(part.getContentType() == null) {
            return null;
        }

        String contentDisposition = part.getHeader("content-disposition");

        if(StringUtils.isEmpty(contentDisposition)) {
            return null;
        }
        //如 form-data; name="file1"; filename="TODO.txt"
        return StringUtils.substringBetween(contentDisposition, "filename=\"", "\"");

    }
}
