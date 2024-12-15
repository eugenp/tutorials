package com.baeldung.annotation.servletcomponentscan.components;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "echo servlet", urlPatterns = "/echo")
public class EchoServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            Path path = File.createTempFile("echo", "tmp").toPath();
            Files.copy(request.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(path, response.getOutputStream());
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
