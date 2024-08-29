package com.baeldung.servlets;

import java.io.BufferedReader;
import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

        try (BufferedReader reader = req.getReader()) {
            Gson gson = new Gson();
            Product newProduct = gson.fromJson(reader, Product.class);
            resp.getWriter()
                .append("Added new Product with name: ")
                .append(newProduct.getName());

        } catch (IOException ex) {
            req.setAttribute("message", "There was an error: " + ex.getMessage());
        }

    }
}
