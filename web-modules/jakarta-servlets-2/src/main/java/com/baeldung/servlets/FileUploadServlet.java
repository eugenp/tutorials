package com.baeldung.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet(name = "FileUploadServlet", urlPatterns = "/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileUploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIRECTORY = "uploads";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        Part filePart = request.getPart("file");
        if (filePart != null) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            if(fileName.isEmpty()){
                response.getWriter().println("Invalid File Name!");
                return;
            }
            // Don't use this code as-is! It is vulnerable to deceptive extensions.
            // See
            // https://cheatsheetseries.owasp.org/cheatsheets/File_Upload_Cheat_Sheet.html
            // to learn how to harden it.
            if(!fileName.endsWith(".txt")){
                response.getWriter().println("Only .txt files are allowed!");
                return;
            }

            File file = new File(uploadPath, fileName);

            // Don't use this code as-is! It is vulnerable to directory traversal.
            // See
            // https://cheatsheetseries.owasp.org/cheatsheets/File_Upload_Cheat_Sheet.html
            // to learn how to harden it.
            try (InputStream fileContent = filePart.getInputStream()) {
                Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                response.getWriter().println("Error writing file: " + e.getMessage());
                return;
            }

            response.getWriter()
                .println("File uploaded to: " + file.toPath());
        } else {
            response.getWriter()
                .println("File upload failed!");
        }
    }
}


