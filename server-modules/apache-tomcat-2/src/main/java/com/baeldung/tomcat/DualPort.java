package com.baeldung.tomcat;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Connector;
import java.io.File;
import java.io.IOException;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

public class DualPort {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(new File("tomcat-temp").getAbsolutePath());

        tomcat.setPort(7080);
        tomcat.getConnector();

        Connector secondConnector = new Connector();
        secondConnector.setPort(8080);
        tomcat.getService().addConnector(secondConnector);

        Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());
        Tomcat.addServlet(ctx, "portServlet", new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                int port = req.getLocalPort();
                resp.setContentType("text/plain");
                resp.getWriter().write("Port: " + port + "\n");
            }
        });
        ctx.addServletMappingDecoded("/", "portServlet");

        tomcat.start();
        System.out.println("✅ Tomcat running on ports 8080 and 7080");
        tomcat.getServer().await();
    }
}

