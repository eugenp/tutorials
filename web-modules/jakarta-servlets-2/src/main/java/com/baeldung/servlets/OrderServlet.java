package com.baeldung.servlets;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baeldung.model.Order;
import com.thoughtworks.xstream.XStream;

@WebServlet(name = "OrderServlet", urlPatterns = "/order")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String contentType = req.getContentType();
        if (!("application/xml".equals(contentType))) {
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

        XStream xStream = new XStream();
        xStream.allowTypesByWildcard(new String[] { "com.baeldung.**" });
        xStream.alias("Order", Order.class);
        Order order = (Order) xStream.fromXML(payload);

        resp.getWriter()
            .append("Created new Order with orderId: ")
            .append(order.getOrderId())
            .append(" for Product: ")
            .append(order.getProduct());
    }
}
