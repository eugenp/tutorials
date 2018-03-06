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
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-8-4 上午9:31
 * <p>Version: 1.0
 */
public class VelocityFramePageletView extends AbstractVelocityPageletView {

    @Override
    public void render(final BigPipeContext context, final HttpServletResponse response) {
        if(getContextType() != null) {
            response.setContentType(getContextType());
        }
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
        }

        StringWriter sw = new StringWriter();

        getTemplate().merge(new VelocityContext(context), sw);

        out.write(sw.toString());
        out.flush();
    }
}
