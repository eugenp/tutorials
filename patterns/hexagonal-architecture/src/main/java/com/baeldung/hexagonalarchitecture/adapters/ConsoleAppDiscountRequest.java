package com.baeldung.hexagonalarchitecture.adapters;

import com.baeldung.hexagonalarchitecture.domain.StoreManager;
import com.baeldung.hexagonalarchitecture.ports.IDiscountProvider;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Adapter is using the IDiscountProvider to connect to business model and request for order prices
 */
public class ConsoleAppDiscountRequest {

    private IDiscountProvider discountSale;

    public ConsoleAppDiscountRequest(IDiscountProvider discountSale) {
        this.discountSale = discountSale;
    }

    public String getPriceMessage(List<Integer> productIds) {
        //Adapter is dealing with user (console application user) I/O enhancement
        return String.format("The price of the order is %s", discountSale.getOrderPrice(productIds));
    }

    public static void main(String[] args) {

        if(args == null || args.length == 0) {
            throw new IllegalArgumentException("Provide a list of valid product ids from the store separated by space");
        }

        ConsoleAppDiscountRequest request = new ConsoleAppDiscountRequest(
                new StoreManager(
                        new InMemoryProductProvider(),
                        new InMemoryPromotionProvider()
                )
        );

        List<Integer> productIds = Arrays.stream(args).map(Integer::parseInt).collect(Collectors.toList());
        System.out.println(request.getPriceMessage(productIds));
    }
}
