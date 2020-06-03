package com.baeldung.jetty.httpclient;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.IO;

public class RequestHandler extends AbstractHandler {

    @Override
    public void handle(String target, Request jettyRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        jettyRequest.setHandled(true);
        response.setContentType(request.getContentType());
        IO.copy(request.getInputStream(), response.getOutputStream());
    }
}