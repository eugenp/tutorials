package com.baeldung.javaeeannotations;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
  name = "BankAccountServlet", 
  description = "Represents a Bank Account and it's transactions", 
  urlPatterns = {"/account", "/bankAccount" }, 
  initParams = { @WebInitParam(name = "type", value = "savings") }
  )
/*@ServletSecurity(
  value = @HttpConstraint(rolesAllowed = {"Member"}),
  httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed = {"Admin"})}
  )*/
public class AccountServlet extends javax.servlet.http.HttpServlet {

    String accountType = null;

    public void init(ServletConfig config) throws ServletException {
        accountType = config.getInitParameter("type");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        writer.println("<html>Hello, I am an AccountServlet!</html>");
        writer.flush();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        double accountBalance = 1000d;
        String paramDepositAmt = request.getParameter("dep");
        double depositAmt = Double.parseDouble(paramDepositAmt);

        accountBalance = accountBalance + depositAmt;
        
        PrintWriter writer = response.getWriter();
        writer.println("<html> Balance of " + accountType + " account is: " + accountBalance + "</html>");
        writer.flush();
    }
}
