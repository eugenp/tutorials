package com.baeldung.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FirstServlet
 */
@WebServlet({ "/", "/hello" })
public class FirstServlet extends HttpServlet {
 private static final long serialVersionUID = 1L;
 //Context is a parent web application folder that is being used by you to serve content
 private static final String context = "/ServletIntro";    
    
    public FirstServlet() {
        super();
    }
    public void init(ServletConfig servletConfig) throws ServletException{
     /*
      * Initialization related code goes here
      */
    }
 /**
  * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss"); 
  request.setAttribute("Time", df.format(new Date())); // This will be available as an attribute on jsp
        //If request URL is <website-address>/context/hello, go inside the if condition
  if(request.getRequestURI().equals(context+"/hello"))
      request.getRequestDispatcher("/WEB-INF/hello.jsp").forward(request, response);
  else
      request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
 }

 /**
  * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // TODO Auto-generated method stub
 }

}
