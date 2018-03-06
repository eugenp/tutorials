/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.bigpipe.view.velocity;

import com.sishuok.bigpipe.BigPipeContext;
import org.apache.velocity.VelocityContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-8-4 上午9:14
 * <p>Version: 1.0
 */
public class VelocityPageletView extends AbstractVelocityPageletView {

    @Override
    public void render(final BigPipeContext context, final HttpServletResponse response) {
        StringWriter sw = new StringWriter();
        getTemplate() .merge(new VelocityContext(context), sw);
        StringBuilder buffer = new StringBuilder();
        /**
         *
         * <script>
         *     writePagelet({
         *         container : "", //html的容器
         *         html : "", //html内容
         *         css  : "", //要引入的css文件
         *         js : ""   // 要引入的js文件
         *
         *     });
         * </script>
         *
         */
        buffer.append("<script>pl.write(");
        buffer.append("{");
        buffer.append("container:\"");
        buffer.append(getPageletResult().getContainer());
        buffer.append("\",");
        buffer.append("html:\"");
        buffer.append(sw.toString().replaceAll("\"", "\\\\\"").replaceAll("[\r\n]", ""));
        buffer.append("\",");
        buffer.append("css:");
        appendArray(buffer, context.getContextPath(), getPageletResult().getCssUrls());
        buffer.append(",");
        buffer.append("js:");
        appendArray(buffer, context.getContextPath(), getPageletResult().getJsUrls());
        buffer.append("}");
        buffer.append(");</script>");
        try {
            response.getWriter().write(buffer.toString());
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
    }
    }

    private void appendArray(StringBuilder buffer, String contextPath, String[] urls) {
        buffer.append("[");
        if(urls != null) {
            int index = 0;
            for(String url : urls) {
                if(index > 0) {
                    buffer.append(",");
                }
                buffer.append("\"");

                if(!url.startsWith("http:") && !url.startsWith("https:") && !url.startsWith(contextPath)) {
                    url = contextPath + url;
                }
                buffer.append(url);
                buffer.append("\"");
            }
        }
        buffer.append("]");
    }
}
