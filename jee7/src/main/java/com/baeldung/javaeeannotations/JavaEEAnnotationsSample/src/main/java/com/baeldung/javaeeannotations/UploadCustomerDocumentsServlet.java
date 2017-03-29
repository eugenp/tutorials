package com.baeldung.javaeeannotations;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(urlPatterns = { "/uploadCustDocs" })
@MultipartConfig(
  fileSizeThreshold = 1024 * 1024 * 20,
  maxFileSize = 1024 * 1024 * 20,
  maxRequestSize = 1024 * 1024 * 25,
  location = "D:/custDocs"
  )
public class UploadCustomerDocumentsServlet extends HttpServlet {

    protected void doPost(
     HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        for (Part part : request.getParts()) {
            part.write("myFile");
        }
      
        PrintWriter writer = response.getWriter();
        writer.println("<html>File uploaded successfully!</html>");
        writer.flush();
    }

}
