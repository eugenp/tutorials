package com.baeldung.servlets;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baeldung.model.Product;
import com.google.gson.Gson;

@WebServlet(name = "ProductServlet", urlPatterns = "/product")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String contentType = req.getContentType();
        if (!("application/json".equals(contentType))) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Invalid content type");
            return;
        }
        String payload = "";
        try (BufferedReader reader = req.getReader()) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            payload = sb.toString();
        } catch (IOException ex) {
            req.setAttribute("message", "There was an error: " + ex.getMessage());
        }

        Gson gson = new Gson();
        Product newProduct = gson.fromJson(payload, Product.class);

        resp.getWriter()
            .append("Added new Product with name: ")
            .append(newProduct.getName());
    }
}
