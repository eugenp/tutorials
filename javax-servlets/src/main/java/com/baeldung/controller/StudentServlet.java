package com.baeldung.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baeldung.service.StudentService;

/**
 * 
 * @author haseeb
 *
 */
@WebServlet(name = "StudentServlet", urlPatterns = "/student-record")
public class StudentServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        StudentService studentService = new StudentService();
        String studentID = request.getParameter("id");
        if (studentID != null) {
            int id = Integer.parseInt(studentID);
            request.setAttribute("studentRecord", studentService.getStudent(id));
        } 
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/student-record.jsp");
        dispatcher.forward(request, response);
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
}
