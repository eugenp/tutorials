package com.baeldung.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.baeldung.domain.Order;
import com.baeldung.service.ShoppingServicePort;

/**
 * Shopping Controller to handle requests from the clients.
 */
@Controller
public class ShoppingControllerAdapter {

    Log logger = LogFactory.getLog(ShoppingControllerAdapter.class);

    @Autowired
    ShoppingServicePort shoppingServicePort;

    @GetMapping(path = "/")
    public String welcome(Model model) {
        return "orderForm";
    }

    @PostMapping(path = "/order")
    public String triggerOrder(HttpServletRequest request) {
        try {
            Order order = shoppingServicePort.placeOrder(request.getParameter("productName"));
            shoppingServicePort.updateInventory();
            logger.info("Order Id is: " + order.getOrderId() + " and Order Placed For: " + order.getProductName());
            return "orderSuccess";
        } catch (Exception e) {
            logger.error("Order Failed");
            return "orderFail";
        }
    }
}