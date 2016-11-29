package com.root;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FormServlet", urlPatterns = "/calculateServlet")
public class FormServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String height = request.getParameter("height");
        String weight = request.getParameter("weight");

        try {
            double bmi = calculateBMI(Double.parseDouble(weight), Double.parseDouble(height));

            request.setAttribute("bmi", bmi);
            response.setHeader("Test", "Success");
            response.setHeader("BMI", String.valueOf(bmi));

            response.setHeader("Test", "Success");
            response.setHeader("BMI", String.valueOf(bmi));

            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {

            response.sendRedirect("index.jsp");
        }
    }

    private Double calculateBMI(Double weight, Double height) {

        return weight / (height * height);
    }
}