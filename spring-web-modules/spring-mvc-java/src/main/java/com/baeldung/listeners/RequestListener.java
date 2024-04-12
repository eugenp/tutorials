package com.baeldung.listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;

@WebListener
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent event) {
    }

    @Override
    public void requestDestroyed(ServletRequestEvent event) {
        HttpServletRequest request = (HttpServletRequest)event.getServletRequest();
        if (!request.getServletPath().equals("/counter")) {
            ServletContext context = event.getServletContext();
            context.setAttribute("counter", (int)context.getAttribute("counter") + 1);
        }
    }
}
