package com.baeldung.application;

import com.baeldung.domain.CoffeeOrderService;

import java.util.Scanner;

/**
 * Main client API of our application.
 * Supports placing an order, listing all orders, completing or canceling an order by id.
 *
 * We can use the CLI application as follows:
 * Place order: PLACE-<coffee type>-<address>
 * List all orders: LIST
 * Complete order: COMPLETE-<order integer id>
 * Cancel order: CANCEL-<order integer id>
 *
 * Note that we must delimit each parameter of the order using a dash '-' character.
 */
public class CoffeeOrderCLI {

    private CoffeeOrderService orderService;

    private CoffeeOrderCLI(CoffeeOrderService coffeeOrderService) {
        this.orderService = coffeeOrderService;
    }

    private void placeOrder(String coffeeType, String address) {
        orderService.addOrder(coffeeType, address);
    }

    private void listAllOrders() {
        orderService.findAll().forEach(order -> System.out.println(order.toString()));
    }

    private void completeOrder(String orderId) {
        orderService.completeOrder(Integer.parseInt(orderId));
    }

    private void cancelOrder(String orderId) {
        orderService.cancelOrder(Integer.parseInt(orderId));
    }

    public static void main(String[] args) {
        System.out.println("Please place your order");
        CoffeeOrderCLI cli = new CoffeeOrderCLI(new CoffeeOrderService(new VirtualOrderRepositoryAdapter()));
        Scanner cliInput = new Scanner(System.in);
        String input;
        while (!(input = cliInput.nextLine()).equalsIgnoreCase("exit")) {
            final String[] orderParameters = input.split("-");
            switch (orderParameters[0].toUpperCase()) {
            case "PLACE":
                cli.placeOrder(orderParameters[1], orderParameters[2]);
                break;
            case "LIST":
                cli.listAllOrders();
                break;
            case "COMPLETE":
                cli.completeOrder(orderParameters[1]);
                break;
            case "CANCEL":
                cli.cancelOrder(orderParameters[1]);
                break;
            default:
                break;
            }
        }
    }

}
