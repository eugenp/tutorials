package com.baeldung.javaeeannotations;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

@WebListener
public class DepositRequestListener implements ServletRequestListener {
    
    public void requestDestroyed(ServletRequestEvent event) {
                
    }

    public void requestInitialized(ServletRequestEvent evet) {
       HttpServletRequest req =  (HttpServletRequest)evet.getServletRequest();
       req.setAttribute("interest", new Double(10));
    }

}
