package com.baeldung.hexagonal;

import com.baeldung.hexagonal.adapter.RegularDiscountCalculator;
import com.baeldung.hexagonal.adapter.SalesDiscountCalculator;
import com.baeldung.hexagonal.application.Application;
import com.baeldung.hexagonal.port.PriceCalculator;
import com.baeldung.hexagonal.port.DiscountCalculator;

public class Runner {
    private static final String[] PARAMETERS = new String[] { "regular", "sales" };
    private static float[] NUMBERS = new float[] { 34.95F, 25F, 59.95F, 67F };

    public static void main(String[] args) {
        System.out.println("Starting application");

        if (args == null || args.length != 1 || (PARAMETERS[0].equalsIgnoreCase(args[0]) == false && PARAMETERS[1].equalsIgnoreCase(args[0]) == false)) {
            System.out.println("Please provide exactly one parameter:");
            System.out.println("  \"" + PARAMETERS[0] + "\" or \"" + PARAMETERS[1] + "\".");
            System.exit(1);
        }

        try {
            DiscountCalculator provider = PARAMETERS[0].equalsIgnoreCase(args[0]) ? new RegularDiscountCalculator() : new SalesDiscountCalculator();

            Application application = new Application(provider);
            useService(application, NUMBERS[0]);
            useService(application, NUMBERS[1]);
            useService(application, NUMBERS[2]);
            useService(application, NUMBERS[3]);
            System.out.println("Starting application");
        } catch (RuntimeException e) {
            System.err.println("Error running application: " + e.getLocalizedMessage());
            e.printStackTrace(System.err);
        }
    }

    private static void useService(PriceCalculator service, float price) {
        float result = service.calculateFinalPrice(price);
        System.out.println("Price calculation: shopping cart price=" + price + ", final price=" + result);
    }
}
